package org.jocean.aliyun.ccs;

import java.io.IOException;

import javax.crypto.Mac;

import org.jocean.idiom.DisposableWrapper;
import org.jocean.idiom.ExceptionUtils;
import org.jocean.netty.util.BufsInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;

import io.netty.buffer.ByteBuf;

public class CCSChatUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CCSChatUtil.class);

    public static byte[] updateDigest(final Mac digest, final Iterable<? extends DisposableWrapper<? extends ByteBuf>> dwbs) {
        try (final BufsInputStream<DisposableWrapper<? extends ByteBuf>> is = new BufsInputStream<>(dwb->dwb.unwrap())) {
            is.appendIterable(dwbs);
            is.markEOS();

            final byte[] bytes = ByteStreams.toByteArray(is);
            digest.update(bytes);
            return bytes;
        } catch (final IOException e) {
            LOG.warn("exception when calc digest, detail: {}", ExceptionUtils.exception2detail(e));
            return new byte[0];
        }
    }
}
