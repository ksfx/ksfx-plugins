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

    public DashboardSystemResources(ServiceProvider serviceProvider)
    {
        this.serviceProvider = serviceProvider;
    }

    public GenericResponse getPublishingData()
    {
        IndexService indexService = serviceProvider.getService(IndexService.class);

        StringBuilder sb = new StringBuilder();
        sb.append("Current Index Queue Size: " + indexService.getCurrentQueueSize());

        return new GenericResponse(sb.toString().getBytes("UTF-8"), "", "text/html", false);
    }
}