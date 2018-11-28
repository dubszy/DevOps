package com.mwaltman.devops.api.delegate.digitalocean;

import com.mwaltman.devops.framework.ExternalApiBundle;
import com.mwaltman.devops.framework.externalapi.digitalocean.ExternalDigitalOceanVolumeApi;
import com.mwaltman.devops.framework.resources.externalapi.ApiResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.DigitalOceanActionResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.request.DigitalOceanSnapshotRequestResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.request.DigitalOceanVolumeActionRequestResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.request.DigitalOceanVolumeByNameDropletActionRequestResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.request.DigitalOceanVolumeRequestResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 * DigitalOcean Volume (Block Storage) API
 */
@Path("api/v1/digitalocean/volumes")
@Produces(MediaType.APPLICATION_JSON)
public class DigitalOceanVolumeApi {

    private final ExternalApiBundle externalApiBundle;

    public DigitalOceanVolumeApi(ExternalApiBundle externalApiBundle) {
        this.externalApiBundle = externalApiBundle;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GET
    public DigitalOceanVolumesResponseResource getVolumes(
            @QueryParam("name") Optional<String> name,
            @QueryParam("region") Optional<String> region) {

        ExternalDigitalOceanVolumeApi volumeApi = externalApiBundle
                .getExternalDigitalOceanVolumeApi();

        if (region.isPresent()) {
            if (name.isPresent()) {
                // If 'name' and 'region' are both present, get a single volume
                // by name (this actually returns an array of volumes with a
                // single volume in it)
                return volumeApi.getVolumeByName(name.get(), region.get());
            }
            // If only region is present, get all volumes in that region
            return volumeApi.getVolumesInRegion(region.get());
        }
        // Otherwise, get all volumes
        return volumeApi.getAllVolumes();
    }

    @GET
    @Path("/{id}")
    public DigitalOceanVolumeResponseResource getVolumeById(@PathParam("id") String id) {
        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .getVolumeById(id);
    }

    @GET
    @Path("/{id}/snapshots")
    public DigitalOceanSnapshotsResponseResource getSnapshotsForVolume(
            @PathParam("id") String volumeId) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .getSnapshotsForVolume(volumeId);
    }

    @POST
    public DigitalOceanVolumeResponseResource createVolume(
            DigitalOceanVolumeRequestResource volume) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .createVolume(volume);
    }

    @POST
    @Path("/{id}/snapshots")
    public DigitalOceanSnapshotResponseResource createSnapshot(
            @PathParam("id") String volumeId,
            DigitalOceanSnapshotRequestResource snapshot) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .createSnapshot(volumeId, snapshot);
    }

    @DELETE
    @Path("/{id}")
    public ApiResponseResource deleteVolume(@PathParam("id") String volumeId) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .deleteVolume(volumeId);
    }

    @DELETE
    public ApiResponseResource deleteVolumeByName(
            @QueryParam("name") String name,
            @QueryParam("region") String region) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .deleteVolumeByName(name, region);
    }

    @GET
    @Path("/{id}/actions")
    public DigitalOceanActionsResponseResource getActions(
            @PathParam("id") String volumeId) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .getVolumeActions(volumeId);
    }

    @GET
    @Path("/{vId}/actions/{aId}")
    public DigitalOceanActionResource getAction(
            @PathParam("vId") String volumeId,
            @PathParam("aId") String actionId) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .getVolumeAction(volumeId, actionId);
    }

    @POST
    @Path("/{id}/actions")
    public DigitalOceanActionResource performAction(
            @PathParam("id") String volumeId,
            DigitalOceanVolumeActionRequestResource volumeAction) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .performVolumeAction(volumeId, volumeAction);
    }

    @POST
    @Path("/actions")
    public DigitalOceanActionResource performActionByName(
            DigitalOceanVolumeByNameDropletActionRequestResource volumeAction) {

        return externalApiBundle
                .getExternalDigitalOceanVolumeApi()
                .performVolumeActionByName(volumeAction);
    }
}
