%{
    if(_delete == 'all') {
        play.modules.siena.SienaFixtures.deleteDatabase()
    } 
}%


%{
    if(_load) {
        play.modules.siena.SienaFixtures.loadModels(_load)       
    }
}%

