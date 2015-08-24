/*******************************************************************************
 *     Cloud Foundry 
 *     Copyright (c) [2009-2014] Pivotal Software, Inc. All Rights Reserved.
 *
 *     This product is licensed to you under the Apache License, Version 2.0 (the "License").
 *     You may not use this product except in compliance with the License.
 *
 *     This product includes a number of subcomponents with
 *     separate copyright notices and license terms. Your use of these
 *     subcomponents is subject to the terms and conditions of the
 *     subcomponent's license, as noted in the LICENSE file.
 *******************************************************************************/
package org.trustedanalytics.org.cloudfoundry.identity.uaa.scim;

import java.util.Collection;

/**
 * @author Dave Syer
 * 
 */
public class SearchResults<T> {

    private Collection<T> resources;
    private int startIndex;
    private int itemsPerPage;
    private int totalResults;
    private Collection<String> schemas;

    public void setResources(Collection<T> resources) {
        this.resources = resources;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setSchemas(Collection<String> schemas) {
        this.schemas = schemas;
    }

    public Collection<String> getSchemas() {
        return schemas;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public Collection<T> getResources() {
        return resources;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("SearchResults[schemas:");
        builder.append(getSchemas());
        builder.append("; count:");
        builder.append(getTotalResults());
        builder.append("; size:");
        builder.append(getResources().size());
        builder.append("; index:");
        builder.append(getStartIndex());
        builder.append("; resources:");
        builder.append(getResources());
        builder.append("; id:");
        builder.append(System.identityHashCode(this));
        builder.append(";]");
        return builder.toString();
    }

}
