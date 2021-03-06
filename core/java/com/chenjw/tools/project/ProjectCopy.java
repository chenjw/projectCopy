package com.chenjw.tools.project;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

public class ProjectCopy {

	private static Properties loadProperties(String config) {
		Properties prop = new Properties();
		try {
			prop.load(ProjectCopy.class.getClassLoader().getResourceAsStream(
					config));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static void copyFolder(String config) {
		File f = new File(ProjectCopy.class.getClassLoader()
				.getResource(config).getPath());
		File[] files = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".properties")) {
					return true;
				} else {
					return false;
				}
			}
		});
		for (File ff : files) {
			String path = config + File.separator + ff.getName();
			copy(path);
			System.out.println(path + " finished!");
		}
	}

	public static void copy(String config) {
		Properties prop = loadProperties(config);
		ProjectPackage originPack = new ProjectPackage(prop, new File(
				prop.getProperty("from_dir")),
				prop.getProperty("from_package"),
				prop.getProperty("from_package"),
				prop.getProperty("from_encoding"));
		ProjectPackage targetPack = new ProjectPackage(prop, new File(
				prop.getProperty("to_dir")), prop.getProperty("to_package"),
				prop.getProperty("to_package"), prop.getProperty("to_encoding"));
		originPack.copyTo(targetPack);
	}

	public static void main(String[] args) {
		ProjectCopy.copyFolder("project/dynaComponent");
		System.out.println("finished!");
	}
}
