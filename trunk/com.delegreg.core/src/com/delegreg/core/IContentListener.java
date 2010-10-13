package com.delegreg.core;

import java.util.EventListener;

public interface IContentListener extends EventListener {

	abstract void contentChanged();
	
}
