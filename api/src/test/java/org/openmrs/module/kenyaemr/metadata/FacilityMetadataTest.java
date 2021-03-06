/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.metadata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link FacilityMetadata}
 */
public class FacilityMetadataTest extends BaseModuleContextSensitiveTest {

	@Autowired
	private CommonMetadata commonMetadata;

	@Autowired
	private FacilityMetadata facilityMetadata;

	@Autowired
	private LocationService locationService;

	@Autowired
	private KenyaEmrService kenyaEmrService;

	/**
	 * Setup each test
	 */
	@Before
	public void setup() throws Exception {
		commonMetadata.install();
	}

	/**
	 * @see org.openmrs.module.kenyaemr.metadata.FacilityMetadata#install()
	 */
	@Test
	public void install_limited_shouldInstallAttributeTypes() throws Exception {
		facilityMetadata.install(false);

		Assert.assertThat(locationService.getLocationAttributeTypeByUuid(FacilityMetadata._LocationAttributeType.MASTER_FACILITY_CODE), notNullValue());

		Context.flushSession();
	}

	/**
	 * @see org.openmrs.module.kenyaemr.metadata.FacilityMetadata#install()
	 *
	 * Ignored because it takes ~1 min
	 */
	@Ignore
	@Test
	public void install_shouldInstallAllMetadata() throws Exception {
		// Pre-check against standard test data
		List<Location> locations = locationService.getAllLocations(true);
		Assert.assertThat(locations, hasSize(3));

		long start = System.currentTimeMillis();
		facilityMetadata.install();
		long time = System.currentTimeMillis() - start;
		System.out.println("** Loaded locations in " + time + " ms **");

		locations = locationService.getAllLocations(true);
		Assert.assertThat(locations, hasSize(9459));

		// Check random location
		Location abelMigwiLab = kenyaEmrService.getLocationByMflCode("10001");
		Assert.assertThat(abelMigwiLab.getName(), is("Abel Migwi Johana Laboratory"));
		Assert.assertThat(abelMigwiLab.getDescription(), is("Laboratory (Stand-alone)"));
		Assert.assertThat(abelMigwiLab.getCountry(), is("Kenya"));
		Assert.assertThat(abelMigwiLab.getStateProvince(), is("Central"));

		Context.flushSession();
		Context.clearSession();

		// Install again...
		start = System.currentTimeMillis();
		facilityMetadata.install();
		time = System.currentTimeMillis() - start;
		System.out.println("** Loaded locations in " + time + " ms **");
	}
}