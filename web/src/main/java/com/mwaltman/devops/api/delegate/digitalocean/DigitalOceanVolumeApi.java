package com.mwaltman.devops.api.delegate.digitalocean;

import com.mwaltman.devops.framework.ExternalApiBundle;
import com.mwaltman.devops.framework.externalapi.digitalocean.ExternalDigitalOceanVolumeApi;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanSnapshotsResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanVolumeResponseResource;
import com.mwaltman.devops.framework.resources.externalapi.digitalocean.response.DigitalOceanVolumesResponseResource;

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
}
