-- Field Type Definition
insert into field_type_definition values (1, 'Number');
insert into field_type_definition values (2, 'Text (single line)');
insert into field_type_definition values (3, 'Text (multi line)');
insert into field_type_definition values (4, 'Address');
insert into field_type_definition values (5, 'Select (single option)');
insert into field_type_definition values (6, 'Select (multi option)');
insert into field_type_definition values (7, 'Boolean');
insert into field_type_definition values (8, 'Email');
insert into field_type_definition values (9, 'Phone Number');
insert into field_type_definition values (10, 'Picture');
insert into field_type_definition values (11, 'Date');
insert into field_type_definition values (12, 'Price');

-- Themes
insert into theme values (1, 'Unterkünfte', 'Unterkünfte');
insert into theme values (2, 'Mitfahrgelegenheiten', 'Mitfahrgelegenheiten');

-- Field Type
insert into field_type values (1, 2, 1, 'Titel', 'Titel', 1, 100, 1, true, false, true, true);
insert into field_type values (2, 3, 1, 'Beschreibung der Unterkunft', 'Beschreibung', 1, 1000, 2, true, false, true, true);
insert into field_type values (3, 11, 1, 'Frei ab Datum', 'Ab Datum', 0, 0, 3, true, true, true, true);
insert into field_type values (4, 11, 1, 'Frei bis Datum', 'Bis Datum', 0, 0, 4, false, false, true, true);
insert into field_type values (5, 1, 1, 'Anzahl Zimmer', 'Zimmer', 1, 10, 5, true, true, false, true);
insert into field_type values (6, 12, 1, 'Preis der Unterkunft', 'Preis', 1, 1000, 6, true, true, false, true);
insert into field_type values (7, 1, 1, 'Grösse der Unterkunft in qm', 'Grösse[qm]', 1, 1000, 7, true, true, false, true);
insert into field_type values (8, 5, 1, 'Art der Unterkunft', 'Art', 0, 0, 8, true, true, true, true);


-- Field Type Choose
insert into field_type_choose values (1, 8, 'Zimmer', 1);
insert into field_type_choose values (2, 8, 'Wohnung', 2);
insert into field_type_choose values (3, 8, 'Haus', 3);
insert into field_type_choose values (4, 8, 'Parkplatz', 4);

