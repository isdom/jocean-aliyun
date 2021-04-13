package org.jocean.aliyun.oss;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import org.jocean.aliyun.oss.OssAPI.PutObjectBuilder;
import org.jocean.http.ByteBufSlice;
import org.jocean.http.MessageBody;
import org.jocean.http.util.RxNettys;
import org.jocean.idiom.DisposableWrapper;
import org.jocean.idiom.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaders;
import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * An {@link OutputStream} which writes data to Oss.
 * <p>
 * A write operation against this stream will occur at the {@code writerIndex}
 * of its underlying buffer and the {@code writerIndex} will increase during
 * the write operation.
 * <p>
 * This stream implements {@link DataOutput} for your convenience.
 * The endianness of the stream is not always big endian but depends on
 * the endianness of the underlying buffer.
 *
 * @see ByteBufInputStream
 */
public class OssOutputStream extends OutputStream {

    private static final Logger LOG = LoggerFactory.getLogger(OssOutputStream.class);

//    private boolean _opened = true;
    private final ReplaySubject<ByteBufSlice> _pipe;
    private final byte[] _buf;
    private int _current_offset = 0;

    public OssOutputStream(final int bufsize, final PutObjectBuilder putpbject, final String contentType, final int contentLength) {
        this._buf = new byte[bufsize];
        this._current_offset = 0;
        this._pipe = ReplaySubject.create();

        putpbject.body(Observable.just(new MessageBody() {
            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String contentType() {
                return contentType;
            }

            @Override
            public int contentLength() {
                return contentLength;
            }

            @Override
            public Observable<? extends ByteBufSlice> content() {
                return _pipe;
            }}))
        .call()
        .subscribe();
    }

    @Override
    public synchronized void flush() throws IOException {
        if (this._current_offset > 0) {
            final CountDownLatch cdl = new CountDownLatch(1);
            final DisposableWrapper<ByteBuf> dwb = RxNettys.wrap4release( Unpooled.wrappedBuffer(this._buf, 0, this._current_offset));

            _pipe.onNext(new ByteBufSlice() {
                @Override
                public void step() {
                    cdl.countDown();
                }

                @Override
                public Iterable<? extends DisposableWrapper<? extends ByteBuf>> element() {
                    return Arrays.asList(dwb);
                }});
            try {
                cdl.await();
            } catch (final InterruptedException e) {
                LOG.warn("exception when flush, detail: {}", ExceptionUtils.exception2detail(e));
            }
            this._current_offset = 0;
        }
    }

    @Override
    public synchronized void write(final byte[] b, int off, int len) throws IOException {
        while (len > 0) {
            final int step = Math.min(this._buf.length - this._current_offset, len);
            System.arraycopy(b, off, this._buf, this._current_offset, step);
            off += step;
            len -= step;
            this._current_offset += step;

            if (this._current_offset >= this._buf.length) {
                // buffer is full, force flush
                flush();
            }
        }
    }

    @Override
    public void write(final byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(final int b) throws IOException {
        write(new byte[]{(byte)b});
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with this stream. The general contract of <code>close</code>
     * is that it closes the output stream. A closed stream cannot perform
     * output operations and cannot be reopened.
     * <p>
     *
     * @exception  IOException  if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        flush();
        _pipe.onCompleted();
        super.close();
    }
}
