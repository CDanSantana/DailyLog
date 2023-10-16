package dailyLog

import grails.gorm.services.Service

@Service(LogEntry)
interface LogEntryService {

    LogEntry get(Serializable id)

    List<LogEntry> list(Map args)

    Long count()

    void delete(Serializable id)

    LogEntry save(LogEntry logEntry)

}