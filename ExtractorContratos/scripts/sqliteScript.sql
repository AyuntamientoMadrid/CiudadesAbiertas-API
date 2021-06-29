
CREATE TABLE contratos_award (
    ikey            VARCHAR (50)    NOT NULL,
    id              VARCHAR (50)    NOT NULL,
    description     VARCHAR (4000)  DEFAULT NULL,
    award_date      DATETIME (6)    DEFAULT NULL,
    value_amount    DECIMAL (12, 2) DEFAULT NULL,
    is_supplier_for VARCHAR (50)    DEFAULT NULL,
    PRIMARY KEY (
        ikey
    ),
    UNIQUE (
        id
    ),
    FOREIGN KEY (
        is_supplier_for
    )
    REFERENCES contratos_organization (id) 
);

CREATE INDEX i_con_awa_is_suplier ON contratos_award (
    is_supplier_for
);




CREATE TABLE `contratos_item` (
  `ikey` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `description` varchar(4000) NOT NULL,
  `has_classification` varchar(400) DEFAULT NULL,
  PRIMARY KEY (
        ikey
    ),
    UNIQUE (
        id
    )
);


CREATE TABLE `contratos_lot` (
  `ikey` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `title` varchar(400) NOT NULL,
  `description` varchar(4000) NOT NULL,
  `tender_id` varchar(50) NOT NULL,
  `has_supplier` varchar(50) DEFAULT NULL,
  `value_amount` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (
        ikey
    ),
    UNIQUE (
        id
    ),
	FOREIGN KEY (
        tender_id
    )
    REFERENCES contratos_tender (id),
    FOREIGN KEY (
        has_supplier
    )
    REFERENCES contratos_award (id) 
);


CREATE INDEX i_con_lot_has_supplier ON contratos_lot (
    has_supplier
);

CREATE INDEX i_con_lot_tender ON contratos_lot (
    tender_id
);


CREATE TABLE `contratos_lot_rel_item` (
  `ikey` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `lot_id` varchar(50) NOT NULL,
  `item_id` varchar(50) NOT NULL,
  PRIMARY KEY (
        ikey
    ),
    UNIQUE (
        id
    ),
	FOREIGN KEY (
        lot_id
    )
    REFERENCES contratos_lot (id),
    FOREIGN KEY (
        item_id
    )
    REFERENCES contratos_item (id) 
);


CREATE INDEX i_con_lot_rel ON contratos_lot_rel_item (
    item_id
);

CREATE INDEX i_con_lot_rel_id ON contratos_lot_rel_item (
    lot_id
);



CREATE TABLE `contratos_organization` (
  `ikey` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `title` varchar(400) NOT NULL,
  `URL` varchar(400) DEFAULT NULL,
  `contactPoint_email` varchar(200) DEFAULT NULL,
  `contactPoint_faxNumber` varchar(200) DEFAULT NULL,
  `contactPoint_telephone` varchar(200) DEFAULT NULL,
  `contactPoint_title` varchar(400) DEFAULT NULL,
  `municipio_id` varchar(50) DEFAULT NULL,
  `municipio_title` varchar(200) DEFAULT NULL,
  `street_address` varchar(200) DEFAULT NULL,
  `postal_code` varchar(10) DEFAULT NULL,
  `portal_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (
        ikey
    ),
    UNIQUE (
        id
    )
);


CREATE TABLE `contratos_process` (
  `ikey` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `identifier` varchar(50) DEFAULT NULL,
  `title` varchar(400) NOT NULL,
  `URL` varchar(400) DEFAULT NULL,
  `description` varchar(4000) DEFAULT NULL,
  `has_tender` varchar(50) DEFAULT NULL,
  `is_buyer_for` varchar(50) NOT NULL,
  PRIMARY KEY (
        ikey
    ),
    UNIQUE (
        id
    ),
	   FOREIGN KEY (
        has_tender
    )
    REFERENCES contratos_tender (id),
    FOREIGN KEY (
        is_buyer_for
    )
    REFERENCES contratos_organization (id) 
);

CREATE INDEX i_con_pro_has_tender ON contratos_process (
    has_tender
);

CREATE INDEX i_con_pro_is_buyer_for ON contratos_process (
    is_buyer_for
);



CREATE TABLE `contratos_tender` (
  `ikey` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `title` varchar(400) NOT NULL,
  `has_supplier` varchar(50) DEFAULT NULL,
  `main_proc_category` varchar(50) DEFAULT NULL,
  `additional_proc_category` varchar(50) DEFAULT NULL,
  `number_of_tenderers` int(11) DEFAULT NULL,
  `proc_method` varchar(200) DEFAULT NULL,
  `proc_method_details` varchar(200) DEFAULT NULL,
  `tender_status` varchar(200) DEFAULT NULL,
  `period_duration_in_days` int(11) DEFAULT NULL,
  `period_end_date` datetime(6) DEFAULT NULL,
  `period_start_date` datetime(6) DEFAULT NULL,
  `value_amount` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (
        ikey
    ),
    UNIQUE (
        id
    ),
	 FOREIGN KEY (
        has_supplier
    )
    REFERENCES contratos_award (id) 
);


CREATE INDEX i_con_ten_has_supplier ON contratos_tender (
    has_supplier
);



CREATE TABLE `contratos_tender_rel_item` (
  `ikey` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `tender_id` varchar(50) NOT NULL,
  `item_id` varchar(50) NOT NULL,
  PRIMARY KEY (
        ikey
    ),
    UNIQUE (
        id
    ),
	 FOREIGN KEY (
        tender_id
    )
    REFERENCES contratos_tender (id),
    FOREIGN KEY (
        item_id
    )
    REFERENCES contratos_item (id) 
);

CREATE INDEX i_con_tender_rel ON contratos_tender_rel_item (
    item_id
);

CREATE INDEX i_con_tender_id ON contratos_tender_rel_item (
    tender_id
);



-------------Datos-------------------------

INSERT INTO `contratos_organization` (`ikey`, `id`, `title`, `URL`, `contactPoint_email`, `contactPoint_faxNumber`, `contactPoint_telephone`, `contactPoint_title`, `municipio_id`, `municipio_title`, `street_address`, `postal_code`, `portal_id`) VALUES
('A28021350', 'A28021350', 'Redondo y García S.A.', 'https://datos.madrid.es/FwFront/portal_egob/new/img/portal_logo_h.png', 'pepe@botello.com', '1123333333', 'El fax no funciona llamar a 1123333335', 'Redondo y García S.A.', '28006', 'Alcobendas', 'CL BLAS DE OTERO 4', '28100', 'PORTAL000101'),
('A78608940', 'A78608940', 'Roal Guarnicionería S.A.', '', 'info@ayto.com', '1123333333', '1123333335', 'Centro Cultural Anabel Segura', '28006', 'Alcobendas', 'CALLE BUSTAMANTE V 16', '28045', 'PORTAL000112'),
('B78342615', 'B78342615', 'TRITOMA, S.L', '', 'info@tritona.com', '333313333', '333313332', 'Ayuntamiento de YYYYYY', '28006', 'Alcobendas', 'CALLE CAMARENA V 10', '28047', 'PORTAL000105'),
('B80762156', 'B80762156', 'Llama y Arco S.L.', 'https://datos.madrid.es/FwFront/portal_egob/new/img/portal_logo_h.png', 'datos@info.com', '', '', 'Llama y Arco S.L.', '28006', 'Alcobendas', 'CALLE ARROYO BELINCOSO NUM 11', '28030', 'PORTAL000119'),
('B83234799', 'B83234799', 'Ferretería Ferayu, S.L', '', 'datos2@info.com', '', '', 'Ferretería Ferayu, S.L.', '28006', 'Alcobendas', 'PLAZA DONOSO V 5', '28029', 'PORTAL000019'),
('LA0000777', 'LA0000777', 'Distrito de Hortaleza', 'http://www.madrid.es', 'datos3@info.com', 'xxx-xxxx-x-xx', 'yy-y-y-yyyy', 'Ayuntamiento de Hortaleza', '28006', 'Alcobendas', 'AVENIDA MORATALAZ V 130', '28030', 'PORTAL000019'),
('LA0007386', 'LA0007386', 'Área de Gobierno de Salud, Seguridad y Emergencias', 'http://www.madrid.es', 'datos3@info.com', '111-222-333', '111-222-335 y 111-222-337', 'Área de Gobierno de Salud, Seguridad y Emergencias', '28006', 'Alcobendas', 'CALLE CAMARENA V 10', '28047', 'PORTAL000019');


INSERT INTO `contratos_award` (`ikey`, `id`, `description`, `award_date`, `value_amount`, `is_supplier_for`) VALUES
('AW1', 'AW1', 'Ser el empresario que ha presentado la mejor oferta al incorporar el precio más bajo, siendo el precio el único criterio de adjudicación establecido en el Pliego de Cláusulas Administrativas Particulares.', '2018-12-11 00:00:00.000000', '131468.12', 'B83234799'),
('AW2', 'AW2', 'Ser el empresario que ha presentado la mejor oferta al incorporar el precio más bajo, siendo el precio el único criterio de adjudicación establecido en el Pliego de Cláusulas Administrativas Particulares y ser la única oferta presentada a este lote.', '2018-12-11 00:00:00.000000', '26260.00', 'A28021350'),
('AW3', 'AW3', 'Ser el empresario que ha presentado la mejor oferta al incorporar el precio más bajo, siendo el precio el único criterio de adjudicación establecido en el Pliego de Cláusulas Administrativas Particulares y ser la única oferta presentada a este lote.', '2018-11-29 00:00:00.000000', '63032.60', 'A78608940'),
('AW4', 'AW4', 'Ser el empresario que ha presentado la mejor oferta al incorporar el precio más bajo, siendo el precio el único criterio de adjudicación establecido en el Pliego de Cláusulas Administrativas Particulares.', '2018-12-11 00:00:00.000000', '35085.60', 'B83234799'),
('AW5', 'AW5', 'No haber recibido ninguna oferta', '2018-11-16 00:00:00.000000', '0.00', NULL),
('AW6', 'AW6', 'Ser el único licitador presentado y admitido y ser el precio el único criterio de adjudicación.', '2018-12-26 00:00:00.000000', '12045.28', 'B80762156'),
('AW7', 'AW7', 'Ser el único licitador presentado y ser el precio el único criterio de adjudicación.', '2018-12-26 00:00:00.000000', '5508.00', 'B80762156'),
('AW8', 'AW8', 'Por ser la oferta mas ventajosa en relación calidad-precio', '2018-12-28 00:00:00.000000', '29630.00', 'B78342615');


INSERT INTO `contratos_item` (`ikey`, `id`, `description`, `has_classification`) VALUES
('IT1', 'IT1', 'Artículos de ferretería', 'C1'),
('IT2', 'IT2', 'Camillas', 'C2'),
('IT3', 'IT3', 'Materiales de madera diversos para construcción', 'C3'),
('IT4', 'IT4', 'Servicios de esparcimiento, culturales y deportivos', 'C4');


INSERT INTO `contratos_tender` (`ikey`, `id`, `title`, `has_supplier`, `main_proc_category`, `additional_proc_category`, `number_of_tenderers`, `proc_method`, `proc_method_details`, `tender_status`, `period_duration_in_days`, `period_end_date`, `period_start_date`, `value_amount`) VALUES
('TN1', 'TN1', 'Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.', NULL, 'Goods', '2233', 9, 'Open', 'Ordinary', 'complete', NULL, '2018-10-01 00:00:00.000000', '2019-12-15 00:00:00.000000', '283162.40'),
('TN2', 'TN2', 'Certamen de teatro abierto de Hortaleza 2019', 'AW8', 'Services', '1111', 3, '', 'Ordinary', 'complete', 119, '2019-01-10 00:00:00.000000', '2019-04-30 00:00:00.000000', '32871.90');

INSERT INTO `contratos_lot` (`ikey`, `id`, `title`, `description`, `tender_id`, `has_supplier`, `value_amount`) VALUES
('LT1', 'LT1', 'Material diverso', 'Material diverso 1', 'TN1', 'AW1', '131468.12'),
('LT2', 'LT2', 'Material auxiliar', 'Material auxiliar 2', 'TN1', 'AW2', '26260.00'),
('LT3', 'LT3', 'Material de guarnicionería', 'Material de guarnicionería 3', 'TN1', 'AW3', '63032.60'),
('LT4', 'LT4', 'Material madera apeos', 'Material madera apeos 4', 'TN1', 'AW4', '35085.60'),
('LT5', 'LT5', 'Material rescate accidentes tráfico', 'Material rescate accidentes tráfico 5', 'TN1', 'AW5', '0.00'),
('LT6', 'LT6', 'Material equipos soldadura rescate en estructuras', 'Material equipos soldadura rescate en estructuras 6', 'TN1', 'AW6', '12045.28'),
('LT7', 'LT7', 'Material lanza térmica y equipo de plasma', 'Material lanza térmica y equipo de plasma 7', 'TN1', 'AW7', '5508.00');

INSERT INTO `contratos_lot_rel_item` (`ikey`, `id`, `lot_id`, `item_id`) VALUES
('00000000000001', '00000000000001', 'LT1', 'IT1'),
('00000000000002', '00000000000002', 'LT1', 'IT2'),
('00000000000003', '00000000000003', 'LT1', 'IT3'),
('00000000000004', '00000000000004', 'LT2', 'IT1'),
('00000000000005', '00000000000005', 'LT2', 'IT2'),
('00000000000006', '00000000000006', 'LT2', 'IT3'),
('00000000000007', '00000000000007', 'LT3', 'IT1'),
('00000000000008', '00000000000008', 'LT3', 'IT2'),
('00000000000009', '00000000000009', 'LT3', 'IT3'),
('00000000000010', '00000000000010', 'LT4', 'IT1'),
('00000000000011', '00000000000011', 'LT4', 'IT2'),
('00000000000012', '00000000000012', 'LT4', 'IT3'),
('00000000000013', '00000000000013', 'LT5', 'IT1'),
('00000000000014', '00000000000014', 'LT5', 'IT2'),
('00000000000015', '00000000000015', 'LT5', 'IT3'),
('00000000000016', '00000000000016', 'LT6', 'IT1'),
('00000000000017', '00000000000017', 'LT6', 'IT2'),
('00000000000018', '00000000000018', 'LT6', 'IT3'),
('00000000000019', '00000000000019', 'LT7', 'IT1'),
('00000000000020', '00000000000020', 'LT7', 'IT2'),
('00000000000021', '00000000000021', 'LT7', 'IT3');

INSERT INTO `contratos_process` (`ikey`, `id`, `identifier`, `title`, `URL`, `description`, `has_tender`, `is_buyer_for`) VALUES
('300-2018-00524', '300-2018-00524', '300/2018/00524', 'Suministro de diverso material de ferretería para la Jefatura del Cuerpo de Bomberos del Ayuntamiento de Madrid.', 'https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=Nc%2F3KT0AQFwBPRBxZ4nJ%2Fg%3D%3D', 'Id licitación: 300/2018/00524 ; Órgano de Contratación: Área de Gobierno de Salud, Seguridad y Emergencias; Importe: 283162.4 EUR; Estado: ADJ', 'TN1', 'LA0007386'),
('300-2018-01097', '300-2018-01097', '300/2018/01097', 'Certamen de teatro abierto de Hortaleza 2019', 'https://contrataciondelestado.es/wps/poc?uri=deeplink:detalle_licitacion&idEvl=iVfsZ9gd80oBPRBxZ4nJ%2Fg%3D%3D', 'Id licitación: 300/2018/01097; Órgano de Contratación: Distrito de Hortaleza; Importe: 32871.9 EUR; Estado: ADJ', 'TN2', 'A28021350');


INSERT INTO `contratos_tender_rel_item` (`ikey`, `id`, `tender_id`, `item_id`) VALUES
('00000000000001', '00000000000001', 'TN1', 'IT1'),
('00000000000002', '00000000000002', 'TN1', 'IT2'),
('00000000000003', '00000000000003', 'TN1', 'IT3'),
('00000000000004', '00000000000004', 'TN2', 'IT4');