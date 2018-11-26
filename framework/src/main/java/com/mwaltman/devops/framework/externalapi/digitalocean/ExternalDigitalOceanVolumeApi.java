package com.mwaltman.devops.framework.externalapi.digitalocean;

import com.mwaltman.devops.framework.externalapi.*;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanRegionResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.request.DigitalOceanSnapshotRequestResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.request.DigitalOceanVolumeRequestResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanSnapshotResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanSnapshotsResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanVolumeResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanVolumesResponseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

import static com.mwaltman.devops.framework.externalapi.RequestType.DELETE;
import static com.mwaltman.devops.framework.externalapi.RequestType.GET;
import static com.mwaltman.devops.framework.externalapi.RequestType.POST;

/**
 * Defines and communicates with API endpoints for the DigitalOcean Volume API.
 * This class handles the building and execution of requests for this API, it is
 * designed to be used via {@code DigitalOceanVolumeApi} in the {@code web}
 * module.
 */
public class ExternalDigitalOceanVolumeApi extends ExternalApi {

    private static final Logger log = LoggerFactory.getLogger(ExternalDigitalOceanVolumeApi.class);

    /**
     * Factory to use to build requests for this API.
     */
    private final DigitalOceanRequestFactory requestFactory;

    /**
     * API client to use when executing requests for this API.
     */
    private final ApiClient apiClient;

    public ExternalDigitalOceanVolumeApi(String authToken, ApiClient apiClient) {
        this.requestFactory = new DigitalOceanRequestFactory(authToken);
        this.apiClient = apiClient;
    }

    /**
     * Get all DigitalOcean block storage volumes.
     *
     * @return Resource containing all volumes
     */
    public DigitalOceanVolumesResponseResource getAllVolumes() {
        HttpResponse response = apiClient.call(
                requestFactory.build(GET, "volumes"));
        return deserializeJson(DigitalOceanVolumesResponseResource.class,
                false,
                response.getContent()).setResponse(response);
    }

    /**
     * Get the DigitalOcean block storage volumes in a specific region.
     *
     * @param regionSlug {@link DigitalOceanRegionResource#slug region} the
     *                   volume is in
     *
     * @return Resource containing all volumes in the specified region
     */
    public DigitalOceanVolumesResponseResource getVolumesInRegion(String regionSlug) {
        HttpResponse response = apiClient.call(
                requestFactory.build(GET, "volumes?region=" + regionSlug));
        return deserializeJson(DigitalOceanVolumesResponseResource.class,
                false,
                response.getContent()).setResponse(response);
    }

    /**
     * Get a specific DigitalOcean block storage volume by ID.
     *
     * @param id ID of the volume
     *
     * @return Resource representing the volume
     */
    public DigitalOceanVolumeResponseResource getVolumeById(String id) {
        HttpResponse response = apiClient.call(
                requestFactory.build(GET, "volumes/" + id));
        return deserializeJson(DigitalOceanVolumeResponseResource.class,
                true,
                response.getContent()).setResponse(response);
    }

    /**
     * Get a specific DigitalOcean block storage volume by name and region. Note
     * that this actually returns an {@link DigitalOceanVolumesResponseResource
     * array of volumes}, but it will only contain one volume.
     *
     * @param name {@link DigitalOceanVolumeResponseResource#name} of the volume
     * @param regionSlug {@link DigitalOceanRegionResource#slug region} the
     *                   volume is in
     *
     * @return Resource representing the volume
     */
    public DigitalOceanVolumesResponseResource getVolumeByName(String name,
                                                      String regionSlug) {
        HttpResponse response = apiClient.call(
                requestFactory.build(GET, "volumes?name=" + name
                        + "&region=" + regionSlug));
        return deserializeJson(DigitalOceanVolumesResponseResource.class,
                false,
                response.getContent()).setResponse(response);
    }

    /**
     * Get the snapshots for a specific DigitalOcean block storage volume.
     *
     * @param volumeId ID of the volume to get the snapshots for
     *
     * @return Resource representing all of the snapshots
     */
    public DigitalOceanSnapshotsResponseResource getSnapshotsForVolume(
            String volumeId) {
        HttpResponse response = apiClient.call(requestFactory.build(GET,
                "volumes/" + volumeId + "/snapshots"));
        return deserializeJson(DigitalOceanSnapshotsResponseResource.class,
                false,
                response.getContent()).setResponse(response);
    }

    /**
     * Create a DigitalOcean block storage volume.
     *
     * @param volume Resource describing the volume to create
     *
     * @return Resource representing the newly created volume
     */
    public DigitalOceanVolumeResponseResource createVolume(
            DigitalOceanVolumeRequestResource volume) {
        HttpResponse response = apiClient.call(
                requestFactory.build(POST,
                        "volumes",
                        serializeJson(volume),
                        MediaType.APPLICATION_JSON));
        return deserializeJson(DigitalOceanVolumeResponseResource.class,
                true,
                response.getContent()).setResponse(response);
    }

    /**
     * Create a snapshot from a DigitalOcean block storage volume.
     *
     * @param volumeId ID of the volume to create the snapshot from
     * @param snapshot Resource describing the snapshot to create
     *
     * @return Resource representing the newly created snapshot
     */
    public DigitalOceanSnapshotResponseResource createSnapshot(
            String volumeId,
            DigitalOceanSnapshotRequestResource snapshot) {
        HttpResponse response = apiClient.call(
                requestFactory.build(POST,
                        "volumes/" + volumeId + "/snapshots",
                        serializeJson(snapshot),
                        MediaType.APPLICATION_JSON));
        return deserializeJson(DigitalOceanSnapshotResponseResource.class,
                true,
                response.getContent()).setResponse(response);
    }

    /**
     * Delete a DigitalOcean block storage volume.
     *
     * @param volumeId ID of the volume to delete
     *
     * @return Resource representing the response; a successful response is 204
     * (no content)
     */
    public ApiResponseResource deleteVolume(String volumeId) {
        HttpResponse response = apiClient.call(
                requestFactory.build(DELETE, "volumes/" + volumeId));
        return new ApiResponseResource().setResponse(response);
    }

    /**
     * Delete a DigitalOcean block storage volume by name.
     *
     * @param name Name of the volume to delete
     * @param regionSlug Region the volume is in
     *
     * @return Resource representing the response; a successful response is 204
     * (no content)
     */
    public ApiResponseResource deleteVolumeByName(String name, String regionSlug) {
        HttpResponse response = apiClient.call(
                requestFactory.build(DELETE, "volumes?name=" + name
                                + "&region=" + regionSlug));
        return new ApiResponseResource().setResponse(response);
    }
}
