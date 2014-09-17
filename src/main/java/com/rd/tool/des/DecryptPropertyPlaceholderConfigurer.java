package com.rd.tool.des;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

public class DecryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	
	private Resource[] locations;
	private Resource keyLocation;
	private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	private String fileEncoding = "utf-8";
	private boolean ignoreResourceNotFound = false;

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	public void setFileEncoding(String encoding) {
		this.fileEncoding = encoding;
	}

	public void setIgnoreResourceNotFound(boolean ignoreResourceNotFound) {
		this.ignoreResourceNotFound = ignoreResourceNotFound;
	}

	public void setKeyLocation(Resource keyLocation) {
		this.keyLocation = keyLocation;
	}

	protected void loadProperties(Properties props) throws IOException {
		if (this.locations != null)
			for (int i = 0; i < this.locations.length; ++i) {
				Resource location = this.locations[i];
				InputStream is = null;
				InputStream keyStream = null;
				InputStream readIs = null;
				try {
					is = location.getInputStream();

					keyStream = this.keyLocation.getInputStream();

					readIs = DesUtil.decrypt(is, DesUtil.getKey(keyStream));

					if (location.getFilename().endsWith(".xml")) {
						this.propertiesPersister.loadFromXml(props, readIs);
					} else if (this.fileEncoding != null)
						this.propertiesPersister
								.load(props, new InputStreamReader(readIs,
										this.fileEncoding));
					else
						this.propertiesPersister.load(props, readIs);
				} catch (Exception ex) {
					if ((this.ignoreResourceNotFound)
							&& (this.logger.isWarnEnabled()))
						this.logger.warn("Could not load properties from "
								+ location + ": " + ex.getMessage());
				} finally {
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(keyStream);
					IOUtils.closeQuietly(readIs);
				}
			}
	}
}