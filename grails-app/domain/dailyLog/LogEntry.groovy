package dailyLog

class LogEntry {
    Date date
    String period
    String content
    Boolean isProtected
    Date updated

    static mapping = {
        content type: "text", sqlType: "clob"
    }

    static constraints = {
        date(nullable: false, blank: false, isEditable: false, editable: false)
        period(nullable: false, blank: false)
        content(nullable: false, blank: false, widget: 'textarea')
        isProtected(nullable: false, blank: false)
        updated(nullable: false, blank: false, widget: 'text', editable: false, isEditable: false)
    }

    LogEntry(){
        this.period = new Date().getHours()
        if(this.period.toInteger() in 6..11) this.period = 'Manhã'
        else if(this.period.toInteger() in 13..18) this.period = 'Tarde'
        else if(this.period.toInteger() in 19..24 || this.period.toInteger() == 0) this.period = 'Noite'
        else if(this.period.toInteger() in 1..5) this.period = 'Madrugada'
        else if(this.period.toInteger() == 12) this.period = 'Meio-dia'
    }

}
