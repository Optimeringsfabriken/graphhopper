package com.graphhopper.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.IsochroneRequest;

import java.io.IOException;

class IsochroneRequestDeserializer extends JsonDeserializer<IsochroneRequest> {
    @Override
    public IsochroneRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        IsochroneRequest request = new IsochroneRequest();
        JsonNode treeNode = jsonParser.readValueAsTree();
        request.setProfileName(treeNode.get("profileName").asText());
        if (treeNode.has("timeLimitInSeconds")) {
            request.setTimeLimitInSeconds(treeNode.get("timeLimitInSeconds").asLong());
        }
        if (treeNode.has("distanceLimitInMeters")) {
            request.setDistanceLimitInMeters(treeNode.get("distanceLimitInMeters").asLong());
        }
        for (JsonNode node : treeNode.get("points")) {
            GHPoint point = new GHPoint(node.get("latitude").asDouble(), node.get("longitude").asDouble());
            request.addPoint(point);
        }
        return request;
    }
}