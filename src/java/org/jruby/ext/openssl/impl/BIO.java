/***** BEGIN LICENSE BLOCK *****
 * Version: CPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Common Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/cpl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2008 Ola Bini <ola.bini@gmail.com>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the CPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the CPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby.ext.openssl.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** c: BIO
 *
 * @author <a href="mailto:ola.bini@gmail.com">Ola Bini</a>
 */
public class BIO {
    private static final class BIOInputStream extends InputStream {
        private BIO bio;

        public BIOInputStream(BIO bio) {
            this.bio = bio;
        }

        @Override
        public int read() throws IOException {
            byte[] buffer = new byte[1];
            int read = bio.read(buffer, 0, 1);
            if(read == 0) {
                return -1;
            }
            return ((int)buffer[0])&0xFF;
        }

        @Override
        public int read(byte[] into) throws IOException {
            return this.read(into, 0, into.length);
        }

        @Override
        public int read(byte[] into, int off, int len) throws IOException {
            int read = bio.read(into, off, len);
            if(read == 0) {
                return -1;
            }
            return read;
        }
    }

    private static final class BIOOutputStream extends OutputStream {
        private BIO bio;

        public BIOOutputStream(BIO bio) {
            this.bio = bio;
        }

        @Override
        public void write(int b) throws IOException {
        }

        @Override
        public void write(byte[] out) throws IOException {
            this.write(out, 0, out.length);
        }

        @Override
        public void write(byte[] out, int off, int len) throws IOException {
            bio.write(out, off, len);
        }
    }

    public static InputStream asInputStream(BIO input) {
        return new BIOInputStream(input);
    }

    public static OutputStream asOutputStream(BIO output) {
        return new BIOOutputStream(output);
    }

    public static BIO base64Filter(BIO real) {
        return new Base64BIOFilter(real);
    }

    public static BIO fromString(String input) {
        MemBIO bio = new MemBIO();
        byte[] buf = null;
        try {
            buf = input.getBytes("ISO8859-1");
            bio.write(buf, 0, buf.length);
        } catch(Exception e) {}
        return bio;
    }

    /** c: BIO_new(BIO_s_mem())
     *
     */
    public static BIO mem() {
        return new MemBIO();
    }
    
    /** c: BIO_flush
     *
     */
    public void flush() throws IOException {
    }

    /** c: SMIME_crlf_copy
     *
     */
    public void crlfCopy(byte[] in, int flags) throws IOException {
        //        throw new UnsupportedOperationException();
    }

    /** c: BIO_gets
     *
     */
    public int gets(byte[] in, int len) throws IOException {
        throw new UnsupportedOperationException();
    }

    /** c: BIO_write
     *
     */
    public int write(byte[] out, int offset, int len) throws IOException {
        throw new UnsupportedOperationException();
    }

    /** c: BIO_read
     *
     */
    public int read(byte[] into, int offset, int len) throws IOException {
        throw new UnsupportedOperationException();
    }

    /** c: BIO_set_mem_eof_return
     *
     */
    public void setMemEofReturn(int value) {
        throw new UnsupportedOperationException();
    }
}// BIO