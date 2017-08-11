/*
 * MIT License
 *
 * Copyright (c) 2016 David Russell
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.gitpitch.services;

import com.gitpitch.models.MarkdownModel;
import com.gitpitch.utils.PitchParams;
import com.gitpitch.utils.YAMLOptions;
import org.apache.commons.io.FilenameUtils;
import java.util.*;
import javax.inject.*;
import play.Logger;
import play.Logger.ALogger;

/*
 * PITCHME.md iframe support service.
 */
@Singleton
public class IframeService {

    private final Logger.ALogger log = Logger.of(this.getClass());

    public String buildBackground(PitchParams pp, String bgUrl) {

        String i = new StringBuffer(MarkdownModel.MD_SPACER)
                .append(MarkdownModel.MD_IFRAME_OPEN)
                .append(bgUrl)
                .append(MarkdownModel.MD_IFRAME_INTERACTIVE)
                .append(MarkdownModel.MD_SPACER)
                .toString();
        log.debug("buildBackground: generated={}", i);
        return i;
    }

    public String buildBackgroundOffline(String md) {

        // TODO 
        return md;
    }

    public boolean background(String md) {
        return md.contains(MarkdownModel.DATA_IFRAME_ATTR);
    }

    public String extractBgUrl(String md,
                               String gitRawBase,
                               MarkdownModel mdm) {

        try {

            String delim = mdm.extractIframeDelim(md);
            return md.substring(delim.length());

        } catch (Exception pex) {
            log.warn("extractIframeBgUrl: ex={}", pex);
            /*
             * Invalid bg syntax, return clean slide delimiter.
             */
            return mdm.isHorizontal(md) ? mdm.horizDelim() : mdm.vertDelim();
        }
    }

}
