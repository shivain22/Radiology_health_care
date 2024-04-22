package com.radiology.health.care.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GenerateDefaultJSON {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();

        rootNode.put("id", 0);
        rootNode.put("userId", 0);

        ArrayNode shiftTimesNode = objectMapper.createArrayNode();
        ObjectNode shiftTimeNode = objectMapper.createObjectNode();
        shiftTimeNode.put("startTime", "HH:MM"); // Default start time
        shiftTimeNode.put("endTime", "HH:MM"); // Default end time
        shiftTimesNode.add(shiftTimeNode);

        rootNode.set("shiftTimes", shiftTimesNode);

        try {
            String jsonOutput = objectMapper.writeValueAsString(rootNode);
            System.out.println(jsonOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
