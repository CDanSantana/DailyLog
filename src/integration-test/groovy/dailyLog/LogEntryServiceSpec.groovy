package dailyLog

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class LogEntryServiceSpec extends Specification {

    LogEntryService logEntryService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new LogEntry(...).save(flush: true, failOnError: true)
        //new LogEntry(...).save(flush: true, failOnError: true)
        //LogEntry logEntry = new LogEntry(...).save(flush: true, failOnError: true)
        //new LogEntry(...).save(flush: true, failOnError: true)
        //new LogEntry(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //logEntry.id
    }

    void "test get"() {
        setupData()

        expect:
        logEntryService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<LogEntry> logEntryList = logEntryService.list(max: 2, offset: 2)

        then:
        logEntryList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        logEntryService.count() == 5
    }

    void "test delete"() {
        Long logEntryId = setupData()

        expect:
        logEntryService.count() == 5

        when:
        logEntryService.delete(logEntryId)
        sessionFactory.currentSession.flush()

        then:
        logEntryService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        LogEntry logEntry = new LogEntry()
        logEntryService.save(logEntry)

        then:
        logEntry.id != null
    }
}
