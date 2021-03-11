INSERT INTO `semantic_field` (`id`, `query`, `field`, `predicate`, `object_reference`, `object_reference_type`, `object_type`, `object_uri`, `blank_node_type`, `blank_node_id`, `blank_property_id`) VALUES
('020300000000001951', 'subvencion', 'id', 'dcterms:identifier', 'id', NULL, 'simple', b'0', NULL, NULL, NULL),
('020300000000012632', 'subvencion', 'title', 'dcterms:title', 'title', NULL, 'simple', b'0', NULL, NULL, NULL),
('020300000000029712', 'subvencion', 'areaId', 'essubv:area', 'https://alzir.dia.fi.upm.es/OpenCitiesAPI/organigrama/organizacion/{areaId}', NULL, 'url', b'1', NULL, NULL, NULL),
('020300000000032133', 'subvencion', 'adjudicatarioId', 'dcterms:identifier', 'adjudicatarioId', NULL, 'blank', b'0', 'owl:Thing', 'nodoAdj', 'essubv:adjudicatario'),
('020300000000048993', 'subvencion', 'adjudicatarioTitle', 'dcterms:title', 'adjudicatarioTitle', NULL, 'blank', b'0', 'owl:Thing', 'nodoAdj', 'essubv:adjudicatario'),
('020300000000059241', 'subvencion', 'importe', 'essubv:importe', 'importe', 'xsd:float', 'simple', b'0', NULL, NULL, NULL),
('020300000000062851', 'subvencion', 'type', 'essubv:Subvencion', 'http://localhost:8080/dynamicAPI/API/query/subvencion/{id}', NULL, NULL, b'1', NULL, NULL, NULL);

INSERT INTO `semantic_rel_prefix` (`id`, `query`, `prefix`) VALUES
('020100000000004641', 'subvencion', 'xsd'),
('020100000000011099', 'subvencion', 'rdf'),
('020100000000026863', 'subvencion', 'owl'),
('020100000000031334', 'subvencion', 'org'),
('020100000000041998', 'subvencion', 'essubv'),
('020100000000054469', 'subvencion', 'espresup'),
('020100000000065647', 'subvencion', 'esadm'),
('020100000000074632', 'subvencion', 'dcterms');


INSERT INTO `semantic_rml` (`id`, `query`, `rml`) VALUES
('020300000000071903', 'subvencion', '@prefix rr: <http://www.w3.org/ns/r2rml#> .\r\n@prefix ex: <http://example.com/> .\r\n@prefix rml: <http://semweb.mmlab.be/ns/rml#> .\r\n@prefix ql: <http://semweb.mmlab.be/ns/ql#> .\r\n@base <http://example.com/base/> .\r\n@prefix xsd:   <http://www.w3.org/2001/XMLSchema#> .\r\n@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\r\n@prefix owl:   <http://www.w3.org/2002/07/owl#> .\r\n@prefix org:   <http://www.w3.org/ns/org#> .\r\n@prefix essubv:   <http://vocab.linkeddata.es/datosabiertos/def/sector-publico/subvencion#> .\r\n@prefix espresup:   <http://vocab.linkeddata.es/datosabiertos/def/hacienda/presupuesto#> .\r\n@prefix esadm:   <http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#> .\r\n@prefix dcterms:   <http://purl.org/dc/terms/> .\r\n\r\n<#TriplesMap1> a rr:TriplesMap;\r\n\r\nrml:logicalSource [\r\n  rml:source "nombreDinamico.csv";\r\n  rml:referenceFormulation ql:CSV\r\n  ];\r\n\r\nrr:subjectMap [\r\n rr:template "http://localhost:8080/dynamicAPI/API/query/subvencion/{id}";\r\nrr:class essubv:Subvencion\r\n];\r\n\r\nrr:predicateObjectMap [\r\nrr:predicate dcterms:identifier ;\r\nrr:objectMap [ rml:reference "id" ]\r\n ];\r\nrr:predicateObjectMap [\r\nrr:predicate dcterms:title ;\r\nrr:objectMap [ rml:reference "title" ]\r\n ];\r\nrr:predicateObjectMap [\r\nrr:predicate essubv:area ;\r\nrr:objectMap [ rr:template "https://alzir.dia.fi.upm.es/OpenCitiesAPI/organigrama/organizacion/{areaId}" ]\r\n ];\r\nrr:predicateObjectMap [\r\n   rr:predicate essubv:adjudicatario;\r\n   rr:objectMap [\r\n    rr:parentTriplesMap <#TriplesMap2>;\r\n    rr:joinCondition [\r\n      rr:child "id";\r\n      rr:parent "id";\r\n      ];\r\n    ]\r\n   ];\r\nrr:predicateObjectMap [\r\nrr:predicate essubv:importe ;\r\nrr:objectMap [ rml:reference "importe"; rr:datatype xsd:float; ]\r\n ].\r\n\r\n\r\n<#TriplesMap2> a rr:TriplesMap;\r\n  rml:logicalSource [\r\n      rml:source "nombreDinamico.csv";\r\n    rml:referenceFormulation ql:CSV\r\n ];\r\n\r\nrr:subjectMap [\r\n  rr:termType rr:BlankNode;\r\n  rr:class owl:Thing\r\n ];\r\n\r\nrr:predicateObjectMap [\r\nrr:predicate dcterms:identifier ;\r\nrr:objectMap [ rml:reference "adjudicatarioId" ]\r\n ];\r\n\r\nrr:predicateObjectMap [\r\nrr:predicate dcterms:title ;\r\nrr:objectMap [ rml:reference "adjudicatarioTitle" ]\r\n ].\r\n');


