package com.solar.builder.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class FileIo {
    public static final int RESPONSE_ENCODING_PDF = 1;
    public static final int RESPONSE_FORCE_DOWNLOAD = 2;
    public static final int RESPONSE_OCTET_STREAM = 3;
    public static final int RESPONSE_ENCODING_PDF_INLINE = 4;
    private static final Log log = LogFactory.getLog(DownloadPDF.class);
	

    /**
     * Writes a file to the response
     * 
     * @param _fullpath
     *            Full path of a file to read
     * @param _response
     *            Http Response to send the bytes to
     * @param _encoding
     *            Encoding type for the response. eg.
     *            FileIo.RESPONSE_ENCODING_PDF
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static void writeFileToServletStream(String _fullpath, HttpServletResponse _response, int _encoding)
            throws IOException, FileNotFoundException {
        File file = new File(_fullpath);
        if (file.exists()) {
            switch (_encoding) {
                case RESPONSE_ENCODING_PDF:
                    _response.setContentType("application/pdf");
                    _response.addHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
                break;
                case RESPONSE_ENCODING_PDF_INLINE:
                    _response.setContentType("application/pdf");
                    _response.addHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                break;
                case RESPONSE_FORCE_DOWNLOAD:
                    _response.setContentType("application/force-download;charset=UTF-8");
                    _response.addHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                break;
                case RESPONSE_OCTET_STREAM:
                    _response.setContentType("application/octet-stream");
                break;
                default:
                    _response.setContentType("text/html");
                break;
            }
            ServletOutputStream os = null;
            try {
                os = _response.getOutputStream();
                writeFileToStream(_fullpath, os);
            } catch (FileNotFoundException fnfe) {
                log.warn("Could not find file: " + _fullpath);
                throw fnfe;
            } catch (IOException ioe) {
                log.warn("Could not write to the output stream");
                throw ioe;
            } finally {
                if (os != null) {
                    try {
                        os.flush();
                        os.close();
                    } catch (Exception ignore) {
                        log.warn("Could not close output stream");
                    }
                }
            }
        } else {
            throw new FileNotFoundException("File doesn't exists." + _fullpath);
        }
    }

    /**
     * Writes a stream to the response
     * 
     * @param _filename
     *            Attachment name to set when pushing stream to response
     * @param _is
     *            Input Stream to read
     * @param _response
     *            Http Response to send the bytes to
     * @param _encoding
     *            Encoding type for the response. eg.
     *            FileIo.RESPONSE_ENCODING_PDF
     */
    public static void writeStreamToServletStream(String _filename, java.io.BufferedInputStream _is,
            HttpServletResponse _response, int _encoding) throws IOException {
        switch (_encoding) {
            case RESPONSE_ENCODING_PDF:
                _response.setContentType("application/pdf");
                _response.addHeader("Content-Disposition", "attachment; filename=\"" + _filename + "\"");
            break;
            case RESPONSE_FORCE_DOWNLOAD:
                _response.setContentType("application/force-download;charset=UTF-8");
                _response.addHeader("Content-Disposition", "inline; filename=\"" + _filename + "\"");
            break;
            case RESPONSE_OCTET_STREAM:
                _response.setContentType("application/octet-stream");
            break;
            default:
                _response.setContentType("text/html");
        }
        ServletOutputStream os = null;
        try {
            os = _response.getOutputStream();
            writeStreamToStream(_is, os);
        } catch (FileNotFoundException fnfe) {
            log.warn("Could not read from file: " + _filename);
            throw fnfe;
        } catch (IOException ioe) {
            log.warn("Could not write to the output stream");
            throw ioe;
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception ignore) {
                    log.warn("Could not close output stream");
                }
            }
        }
    }

    /**
     * Sends a file contents to the OurputStream
     * 
     * @param _fullpath
     *            full path to the file to read
     * @param _os
     *            Output Stream to send the bytes to
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static void writeFileToStream(String _fullpath, java.io.OutputStream _os) throws IOException,
            FileNotFoundException {
        BufferedInputStream isfile = null;
        try {
            File file = new File(_fullpath);
            isfile = new BufferedInputStream(new FileInputStream(file));
            writeStreamToStream(isfile, _os);
        } catch (FileNotFoundException fnfe) {
            log.warn("Could not find file:" + _fullpath);
            throw fnfe;
        } catch (IOException ioe) {
            log.warn("Could not write to the output stream");
            throw ioe;
        } catch (Exception e) {
            throw new IOException("Other exception:" + e.getMessage());
        } finally {
            if (isfile != null) {
                try {
                    isfile.close();
                } catch (Exception ignore) {
                    log.warn("Could not close input stream");
                }
            }
        }
    }

    /**
     * Sends a InputStream to the OurputStream
     * 
     * @param _is
     *            Input Stream to read
     * @param _os
     *            Output Stream to send the bytes to
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static void writeStreamToStream(java.io.BufferedInputStream _is, java.io.OutputStream _os)
            throws IOException, FileNotFoundException {
        try {
            byte[] buff = new byte[10240];
            int bytesread = -1;
            // read from the file; write to the OutputStream
            while ((bytesread = _is.read(buff, 0, 10240)) != -1)
                _os.write(buff, 0, bytesread);
        } catch (IOException ioe) {
            log.warn("Could not read from file stream");
            throw ioe;
        } catch (Exception e) {
            throw new IOException("Other exception:" + e.getMessage());
        } finally {
            if (_is != null) {
                try {
                    _is.close();
                } catch (Exception ignore) {
                    log.warn("Could not close input stream");
                }
            }
        }
    }
}
