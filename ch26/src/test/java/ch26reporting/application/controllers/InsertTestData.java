package ch26reporting.application.controllers;

import java.util.List;
import java.util.UUID;

/*
 * Inserts test diagnosis events into Event Store.
 * Requires event store to be running on port 2113.
 */
public class InsertTestData {
     static final String diagnosesStreamUrl = "http://localhost:2113/streams/diagnoses";

    public static void insert() {
        // Push DiagnosisEvents List to Event Store
//        var request = (HttpWebRequest)WebRequest.Create(diagnosesStreamUrl);
//        request.ContentType = "application/json";
//        request.Method = "POST";
//
//        var json = Json.Encode(DiagnosisEvents);
//
//        using (var sr = new StreamWriter(request.GetRequestStream()))
//        {
//            sr.Write(json);
//            sr.Flush();
//            sr.Close();
//        }
//        request.GetResponse();
    }


    // model of an event - used to push data into ES not used for the report
    static class Diagnosis {
        public UUID eventId;
        public String eventType;
        public DiagnosisData data;
    }

    static class DiagnosisData {
        public String DiagnosisId;
        public String DiagnosisName;
        public String DoctorId;
        public String DoctorName;
        public String Date;
    }


    static final List<Diagnosis> diagnosisEvents = List.of(
            new Diagnosis() {{
                eventId = UUID.randomUUID();
                eventType = "diagnosis";
                data = new DiagnosisData() {{
                    DiagnosisId = "dg1";
                    DiagnosisName = "Eczema";
                    DoctorId = "doc1";
                    DoctorName = "D.C. Green";
                    Date = "2014/02";
                }};
            }},

            new Diagnosis() {{
                eventId = UUID.randomUUID();
                eventType = "diagnosis";
                data = new DiagnosisData() {{
                    DiagnosisId = "dg2";
                    DiagnosisName = "Vertigo";
                    DoctorId = "doc1";
                    DoctorName = "D.C. Green";
                    Date = "2014/02";
                }};
            }},

            new Diagnosis() {{
                eventId = UUID.randomUUID();
                eventType = "diagnosis";
                data = new DiagnosisData() {{
                    DiagnosisId = "dg1";
                    DiagnosisName = "Eczema";
                    DoctorId = "doc2";
                    DoctorName = "J.P. Finch";
                    Date = "2014/03";
                }};
            }},

            new Diagnosis() {{
                eventId = UUID.randomUUID();
                eventType = "diagnosis";
                data = new DiagnosisData() {{
                    DiagnosisId = "dg1";
                    DiagnosisName = "Eczema";
                    DoctorId = "doc2";
                    DoctorName = "J.P. Finch";
                    Date = "2014/03";
                }};
            }},

            new Diagnosis() {{
                eventId = UUID.randomUUID();
                eventType = "diagnosis";
                data = new DiagnosisData() {{
                    DiagnosisId = "dg3";
                    DiagnosisName = "Hypochondriac";
                    DoctorId = "doc3";
                    DoctorName = "U.B Retters";
                    Date = "2014/04";
                }};
            }}
    );
    /*
    Event Store Projections:

    DiagnosesByMonth:
    fromStream('diagnoses')
    .whenAny(function(state, ev) {
        var date = ev.data.Date.replace('/', '');
        var diagnosisId = ev.data.DiagnosisId;
        linkTo('diagnosis-' + diagnosisId + '_' + date, ev);
    });

    DiagnosesByMonthCounts:
    fromCategory('diagnosis')
    .foreachStream()
    .when({
         $init : function(s,e) {return {count : 0}},
         "diagnosis" : function(s,e) { s.count += 1} //mutate in place works
    });

    Months:
    fromStream('diagnoses')
    .whenAny(function(state, ev) {
        var date = ev.data.Date.replace('/', '');
        linkTo('month-' + date, ev);
    });

    MonthsCounts:
    fromCategory('month')
    .foreachStream()
    .when({
        $init : function(s,e) {return {count : 0}},
    "diagnosis" : function(s,e) { s.count += 1 } //mutate in place works
    });
     */
}
