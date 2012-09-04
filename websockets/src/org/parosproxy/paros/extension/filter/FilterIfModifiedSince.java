/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 Chinotec Technologies Company
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Clarified Artistic License
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Clarified Artistic License for more details.
 * 
 * You should have received a copy of the Clarified Artistic License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
// ZAP: 2012/04/25 Added @Override annotation to all appropriate methods.
package org.parosproxy.paros.extension.filter;

import org.parosproxy.paros.Constant;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FilterIfModifiedSince extends FilterAdaptor {

    /* (non-Javadoc)
     * @see org.parosproxy.paros.extension.filter.AbstractFilter#getId()
     */
    @Override
    public int getId() {
        return 10;
    }

    /* (non-Javadoc)
     * @see org.parosproxy.paros.extension.filter.AbstractFilter#getName()
     */
    @Override
    public String getName() {
        return Constant.messages.getString("filter.nocache.name");
        
    }

    /* (non-Javadoc)
     * @see org.parosproxy.paros.core.proxy.ProxyListener#onHttpRequestSend(org.parosproxy.paros.network.HttpMessage)
     */
    @Override
    public void onHttpRequestSend(HttpMessage httpMessage) {
        HttpRequestHeader reqHeader = httpMessage.getRequestHeader();
      	if (!reqHeader.isEmpty() && reqHeader.isText()){
      		String ifModifed = reqHeader.getHeader(HttpHeader.IF_MODIFIED_SINCE);
      		if (ifModifed != null){    
      			reqHeader.setHeader(HttpHeader.IF_MODIFIED_SINCE, null);                   
      		}
      		String ifNoneMatch = reqHeader.getHeader(HttpHeader.IF_NONE_MATCH);
      		if (ifNoneMatch != null){    
      			reqHeader.setHeader(HttpHeader.IF_NONE_MATCH, null);                   
      		}
      		
      	}

    }

    /* (non-Javadoc)
     * @see org.parosproxy.paros.core.proxy.ProxyListener#onHttpResponseReceive(org.parosproxy.paros.network.HttpMessage)
     */
    @Override
    public void onHttpResponseReceive(HttpMessage httpMessage) {

    }

}