/**
 *
 * Copyright (C) 2011-2017 KSFX. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import ch.ksfx.dao.GenericDataStoreDAO
import ch.ksfx.model.GenericDataStore
import ch.ksfx.model.publishing.PublishingStrategy
import ch.ksfx.model.publishing.PublishingSharedData
import ch.ksfx.services.ServiceProvider
import ch.ksfx.services.lucene.IndexService
import ch.ksfx.util.Console
import ch.ksfx.util.GenericResponse
import ch.ksfx.util.PublishingDataShare

class DashboardSystemResources implements PublishingStrategy
{
    private ServiceProvider serviceProvider;
    private GenericDataStoreDAO genericDataStoreDAO;

    private Long megaByte = 1024l * 1024l;

    public DashboardSystemResources(ServiceProvider serviceProvider)
    {
        this.serviceProvider = serviceProvider;
        this.genericDataStoreDAO = serviceProvider.getService(GenericDataStoreDAO.class);
    }

    public GenericResponse getPublishingData()
    {
        IndexService indexService = serviceProvider.getService(IndexService.class);

        StringBuilder sb = new StringBuilder();
        sb.append("<div class='list-group'>");
            sb.append("<div class='list-group-item'>");
                sb.append("Series stats:").append("<br />");
                sb.append("Total series: ").append(getGenericDataStoreInformation("totalSeriesCount")).append("<br />");
                sb.append("Total Observations: ").append(getGenericDataStoreInformation("totalObservationsCount")).append("<br />");
            sb.append("</div>");
            sb.append("<div class='list-group-item'>");
                sb.append("Memory stats:").append("<br />");
                sb.append("Free memory: ").append(getFreeMemory()).append("<br />");
                sb.append("Used memory: ").append(getUsedMemory()).append("<br />");
                sb.append("Total memory: ").append(getTotalMemory()).append("<br />");
                sb.append("Max memory: ").append(getMaxMemory()).append("<br />");
            sb.append("</div>");
            sb.append("<div class='list-group-item'>");
                sb.append("Current Index Queue Size: " + indexService.getCurrentQueueSize());
            sb.append("</div>");
        sb.append("</div>");

        return new GenericResponse(sb.toString().getBytes("UTF-8"), "", "text/html", false);
    }

    public Long getFreeMemory()
    {
        Runtime runtime = Runtime.getRuntime();

        return (runtime.freeMemory() / megaByte);
    }

    public Long getUsedMemory()
    {
        Runtime runtime = Runtime.getRuntime();

        return ((runtime.totalMemory() - runtime.freeMemory()) / megaByte);
    }

    public Long getTotalMemory()
    {
        Runtime runtime = Runtime.getRuntime();

        return (runtime.totalMemory() / megaByte);
    }

    public Long getMaxMemory()
    {
        Runtime runtime = Runtime.getRuntime();

        return (runtime.maxMemory() / megaByte);
    }

    public String getGenericDataStoreInformation(String key)
    {
        GenericDataStore genericDataStore = genericDataStoreDAO.getGenericDataStoreForKey(key);

        if (genericDataStore == null) {
            return "";
        }

        return genericDataStore.getDataValue();
    }
}