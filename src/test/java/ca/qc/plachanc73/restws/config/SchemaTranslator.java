package ca.qc.plachanc73.restws.config;

import java.io.File;
import java.util.EnumSet;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.io.FileUtils;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;

import ca.qc.plachanc73.restws.data.entity.Booking;

public class SchemaTranslator {

	private static final String dbSchemaFilename = "src/test/resources/db-schema.sql";

	/**
	 * Add annotated classes to the Metadata Sources.
	 *
	 * @param metadataSources
	 */
	private static void addAnnotatedClasses(MetadataSources metadataSources) {
		final Reflections commonReflections = new Reflections(Booking.class.getPackage().getName());
		final Set<Class<?>> commonEntityClasses = commonReflections.getTypesAnnotatedWith(Entity.class);
		for (final Class<?> clazz : commonEntityClasses) {
			metadataSources.addAnnotatedClass(clazz);
		}
	}

	public static void main(String[] args) {
		MetadataSources metadataSources = new MetadataSources(new StandardServiceRegistryBuilder()
				.applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect").build());

		addAnnotatedClasses(metadataSources);

		File dbSchemaToDelete = FileUtils.getFile(dbSchemaFilename);
		FileUtils.deleteQuietly(dbSchemaToDelete);

		new SchemaExport().setFormat(true).setDelimiter(";").setOutputFile(dbSchemaFilename)
				.execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.BOTH, metadataSources.buildMetadata());
	}
}