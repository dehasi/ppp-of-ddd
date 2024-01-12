 fromStream('diagnoses')
.whenAny(function(state, ev) {
 var date = ev.data.Date.replace('/', '');
 var diagnosisId = ev.data.DiagnosisId;
 linkTo('diagnosis-' + diagnosisId + '_' + date, ev);
});

fromStream('diagnoses')
.when({
    $any: function(state, event) {
         var date = ev.data.Date.replace('/', '');
         var diagnosisId = ev.data.DiagnosisId;
         linkTo('diagnosis-' + diagnosisId + '_' + date, ev);
    }
});