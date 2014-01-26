/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.tools.xjc.api;

import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JClass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;

/**
 * {@link JAXBModel} that exposes additional information available
 * only for the schema->java direction.
 *
 * @author Kohsuke Kawaguchi
 */
public interface S2JJAXBModel extends JAXBModel {

    /**
     * Gets a {@link Mapping} object for the given global element.
     *
     * @return
     *      null if the element name is not a defined global element in the schema.
     */
    Mapping get( QName elementName );

    /**
     * Gets all the <tt>ObjectFactory</tt> classes generated by the compilation.
     *
     * <p>
     * This should be used for generating {@link XmlSeeAlso} on the SEI.
     */
    List<JClass> getAllObjectFactories();


    /**
     * Gets a read-only view of all the {@link Mapping}s.
     */
    Collection<? extends Mapping> getMappings();

    /**
     * Returns the fully-qualified name of the Java type that is bound to the
     * specified XML type.
     *
     * @param xmlTypeName
     *      must not be null.
     * @return
     *      null if the XML type is not bound to any Java type.
     */
    TypeAndAnnotation getJavaType(QName xmlTypeName);

    /**
     * Generates artifacts.
     *
     * <p>
     * TODO: if JAXB supports various modes of code generations
     * (such as public interface only or implementation only or
     * etc), we should define bit flags to control those.
     *
     * <p>
     * This operation is only supported for a model built from a schema.
     *
     * @param extensions
     *      The JAXB RI extensions to run. This can be null or empty
     *      array if the caller wishes not to run any extension.
     *      <br>
     *
     *      Those specified extensions
     *      will participate in the code generation. Specifying an extension
     *      in this list has the same effect of turning that extension on
     *      via command line.
     *      <br>
     *
     *      It is the caller's responsibility to configure each augmenter
     *      properly by using {@link Plugin#parseArgument(Options, String[], int)}.
     *
     * @return
     *      object filled with the generated code. Use
     *      {@link JCodeModel#build(CodeWriter)} to write them
     *      to a disk.
     */
    JCodeModel generateCode( Plugin[] extensions, ErrorListener errorListener );
}