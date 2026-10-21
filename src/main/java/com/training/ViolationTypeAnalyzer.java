package com.training;

/*
==========
Background
==========
A taxonomy (or taxonomical classification) is a scheme of classification, especially a
hierarchical classification,in which things are organized into groups or types.
For example:
* Ball > Basketball
* Animal > Mammal > Dog
Airbnb maintains a similar taxonomy of policy violation types to help keep track of
offense categories. For example, such classifications might be:
* Host Issue > Cleanliness > Dust.
* Host Issue > Vermin > Bed Bugs

                host_issue    guest_issue
              /    |      \      |
    cleanliness  vermin  commitment
   /      \     /    \
dust   mold  rats bed_bugs

We would like to implement this in code so that we have a convenient API for traversing
policy violation types for internal tooling applications.
========
Question
========
Design a Taxonomy class for storing/accessing violation type metadata.
It should support the following methods / features:
1. Taxonomy(List<ViolationType> violation_types)
2. ViolationType getViolationType(String violationTypeId)
3. List<ViolationType> getSubTypes(String violationTypeId)
4. List<ViolationType> getParentTypes(String violationTypeId)
*/

import java.util.*;
import java.util.stream.Collectors;

public class ViolationTypeAnalyzer {

    public static class ViolationType {
        public String violationTypeId;
        public List<String> subTypeIds;

        public ViolationType(String violationTypeId, List<String> subTypeIds) {
            this.violationTypeId = violationTypeId;
            this.subTypeIds = subTypeIds;
        }
    }

    public static class Taxonomy {
        public HashMap<String, ViolationType> idToViolationType = new HashMap<>();
        public HashMap<String, List<String>> childToParentMap = new HashMap<>();
        public List<String> leafNodes = new ArrayList<>();
        public List<String> leafNodesFromMap = new ArrayList<>();
        public List<String> leafNodesWithFunctional = new ArrayList<>();

        public Taxonomy(List<ViolationType> violationTypes) {

            for(ViolationType violationType : violationTypes) {
                idToViolationType.put(violationType.violationTypeId, violationType);

                if(violationType.subTypeIds.isEmpty()) {
                    leafNodes.add(violationType.violationTypeId);
                } else {
                    for(String subTypeId : violationType.subTypeIds) {
                        childToParentMap
                                .computeIfAbsent(subTypeId, k -> new ArrayList<>())
                                .add(violationType.violationTypeId);
                    }
                }
            }

            for(Map.Entry<String, ViolationType> entry : idToViolationType.entrySet()) {
                if(entry.getValue().subTypeIds.isEmpty()) {
                    leafNodesFromMap.add(entry.getValue().violationTypeId);
                }
            }

            leafNodesWithFunctional = idToViolationType.values().stream().
                    filter(violationType -> violationType.subTypeIds.isEmpty())
                    .map(violationType -> violationType.violationTypeId)
                    .collect(Collectors.toList());

        }

        // Initializes example violation types
        //                host_issue    guest_issue
        //              /    |      \      |
        //    cleanliness  vermin  commitment
        //   /      \     /    \
        // dust   mold  rats bed_bugs

        public List<String> leafAncestorList(String violationId) {
            List<String> ancestorList = new ArrayList<>();
            String temp = violationId;

            while(!getParentTypes(temp).isEmpty()) {
                for(ViolationType parentViolationTypeId : getParentTypes(temp)) {
                    ancestorList.add(parentViolationTypeId.violationTypeId);
                    temp = parentViolationTypeId.violationTypeId;
                }
            }

            return ancestorList;
        }

        public ViolationType getViolationType(String violationTypeId) {
            return idToViolationType.get(violationTypeId);
        }

        public List<ViolationType> getSubTypes(String violationId) {
            ViolationType violationType = getViolationType(violationId);
            List<String> subTypes = violationType.subTypeIds;
            return subTypes.stream()
                    .map(idToViolationType::get)
                    .collect(Collectors.toList());
        }

        public List<ViolationType> getParentTypes(String violationTypeId) {
            List<String> parentIds = childToParentMap
                    .getOrDefault(violationTypeId, Collections.emptyList());
            return parentIds.stream()
                    .map(idToViolationType::get)
                    .collect(Collectors.toList());

        }
    }

    public static void main(String[] args) {
        // Initializes example violation types
        //                host_issue    guest_issue
        //              /    |      \      |
        //    cleanliness  vermin  commitment
        //   /      \     /    \
        // dust   mold  rats bed_bugs

        ViolationType hostIssue = new ViolationType("host_issue", Arrays.asList("cleanliness", "vermin", "commitment"));
        ViolationType guestIssue = new ViolationType("guest_issue", Arrays.asList("commitment"));
        ViolationType cleanliness = new ViolationType("cleanliness", Arrays.asList("dust", "mold"));
        ViolationType vermin = new ViolationType("vermin", Arrays.asList("rats", "bed_bugs"));
        ViolationType commitment = new ViolationType("commitment", Arrays.asList());
        ViolationType dust = new ViolationType("dust", Arrays.asList());
        ViolationType mold = new ViolationType("mold", Arrays.asList());
        ViolationType rats = new ViolationType("rats", Arrays.asList());
        ViolationType bedBugs = new ViolationType("bed_bugs", Arrays.asList());
        //ViolationType bedBugs = new ViolationType("bed_bugs", Arrays.asList("host_issue"));
        List<ViolationType> violationTypes = Arrays.asList(
                hostIssue,
                guestIssue,
                cleanliness,
                vermin,
                commitment,
                dust,
                mold,
                rats,
                bedBugs
        );

        // Set up and test Taxonomy class
        Taxonomy taxonomy = new Taxonomy(violationTypes);
        System.out.println(taxonomy.leafAncestorList("rats"));
        // TODO: Write tests?
    }



}
