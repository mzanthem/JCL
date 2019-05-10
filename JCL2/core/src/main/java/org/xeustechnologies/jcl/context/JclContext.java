/**
 *
 * Copyright 2015 Kamran Zafar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xeustechnologies.jcl.context;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.exception.JclContextException;

/**
 * JclContext holds all the JarClassLoader instances so that they can be
 * accessed from anywhere in the application.
 * 
 * @author Kamran
 * 
 */
public class JclContext {
	/** logger **/
	private static final Logger logger = LoggerFactory.getLogger(JclContext.class);
	/** using ConcurrentHashMap replace of Collections.synchronizedMap **/
    private static final Map<String, JarClassLoader> loaders = new ConcurrentHashMap<String, JarClassLoader>();
    
    public static final String DEFAULT_NAME = "jcl";

    /**
     * singleton
     */
    private static class SingletonHolder {
    	private final static JclContext instance = new JclContext();
    }
    public static JclContext getInstance() {
		return SingletonHolder.instance;
	}
    
    
    private JclContext() {
        validate();
    }
    /**
     * forbid client to create multiple instance
     */
    private void validate() {
        if( isLoaded() ) {
            throw new JclContextException( "Context already loaded. Destroy the existing context to create a new one." );
        }
    }

    public static boolean isLoaded() {
        return !loaders.isEmpty();
    }

    /**
     * check ${jarClassName} is whether or not added to jclContext
     * @param name
     * @return
     */
    public static boolean isAdded(String name) {
    	if (isLoaded()) {
    		if (null != get(name)) {
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Populates the context with JarClassLoader instances
     * 
     * it's not static method, so you must get an instance before add a jarClassLoader
     * @param name
     * @param jcl
     */
    public void addJcl(String name, JarClassLoader jcl) {
    	logger.debug("add jarClassLoader: {}", name);
        if( loaders.containsKey( name ) )
            throw new JclContextException( "JarClassLoader[" + name + "] already exist. Name must be unique" );

        loaders.put( name, jcl );
    }

    /**
     * remove the JarClassLoader by name
     * @param name
     */
    public static void removeJcl(String name) {
    	logger.debug("remove jarClassLoader: {}", name);
		if (loaders.containsKey(name)) {
			loaders.remove(name);
		}
	}
    
    /**
     * Clears the context
     */
    public static void destroy() {
    	logger.debug("destroy all jarClassLoader");
        if( isLoaded() ) {
            loaders.clear();
        }
    }

    /**
     * get default JarClassLoader
     * @return
     */
    public static JarClassLoader get() {
    	if (!loaders.containsKey(DEFAULT_NAME)) {
    		loaders.put(DEFAULT_NAME, new JarClassLoader());
    	}
        return loaders.get( DEFAULT_NAME );
    }

    /**
     * get named JarClassLoader
     * @param name
     * @return
     */
    public static JarClassLoader get(String name) {
        return loaders.get( name );
    }

    
    // -------- monitor --------
    
    public static Map<String, JarClassLoader> getAll() {
        return Collections.unmodifiableMap( loaders );
    }
    /**
     * get all jarClassLoader names
     * @return
     */
    public static Set<String> getAllJclNames() {
    	return Collections.unmodifiableSet(loaders.keySet());
    }
}
