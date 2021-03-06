/**
 *  Copyright 2005-2018 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.jboss.fuse.patch.itests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

import static org.junit.Assert.assertEquals;

/**
 * Integration tests for patching bundles using the patch:* commands.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class PatchBundlesIntegrationTest extends AbstractPatchIntegrationTest {

    @Before
    public void createPatchZipFiles() throws IOException {
        super.createPatchZipFiles();
    }

    @Test
    public void testInstallAndRollbackPatch01() throws Exception {
        load(context, "patch-01");

        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());

        install("patch-01");
        assertEquals("1.0.1", getPatchableBundle().getVersion().toString());

        rollback("patch-01");
        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());
    }

    @Test
    public void testInstallAndRollbackPatch02() throws Exception {
        load(context, "patch-02");

        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());

        install("patch-02");
        assertEquals("1.1.2", getPatchableBundle().getVersion().toString());

        rollback("patch-02");
        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());
    }

    @Test
    public void testInstallAndRollbackPatch02WithoutRange() throws Exception {
        load(context, "patch-02-without-range");

        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());

        install("patch-02-without-range");
        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());

        rollback("patch-02-without-range");
        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());
    }

    @Test
    public void testInstallAndRollbackPatch01And02() throws Exception {
        load(context, "patch-01");
        load(context, "patch-02");

        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());

        install("patch-01");
        assertEquals("1.0.1", getPatchableBundle().getVersion().toString());

        install("patch-02");
        assertEquals("1.1.2", getPatchableBundle().getVersion().toString());

        rollback("patch-02");
        assertEquals("1.0.1", getPatchableBundle().getVersion().toString());

        rollback("patch-01");
        assertEquals("1.0.0", getPatchableBundle().getVersion().toString());
    }

    // Reinstall version 1.0.0 of the 'patchable' bundle and return the bundle id
    @Before
    public void installPatchableBundle() throws Exception {
        super.installPatchableBundle();
    }

}
