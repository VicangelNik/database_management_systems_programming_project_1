package com.vicangel.database_management_systems_pp1.rest.dto.request;

public record BoundingBoxRequest(Double southLatitude, // bottom
                                 Double northLatitude, // top
                                 Double westLongitude, // left
                                 Double eastLongitude) { // right
}
