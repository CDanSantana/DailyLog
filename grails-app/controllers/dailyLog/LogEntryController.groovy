package dailyLog

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.time.*
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
class LogEntryController {

    LogEntryService logEntryService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if(!params?.id) respond logEntryService.list(params), model:[logEntryCount: logEntryService.count(), today: 'Todos os Dias']
        else {
            def date = Date.parse('yyyy-MM-dd', params.id) //(Date) new java.text.SimpleDateFormat('yyyy-MM-dd').parse(params.id)
            date.clearTime()
            def result = LogEntry.createCriteria().list(params){
                and {
                    ge('date', date)
                    le('date', date+1)
                }
            }
            respond result, model:[logEntryCount: result.size(), today: params.id[8..9]+'/'+params.id[5..6]+'/'+params.id[0..3]]
        }
    }

    def show(Long id) {
        def logEntry = logEntryService.get(id)
        if(!logEntry.isProtected) respond logEntry
        else render(model: [id: logEntry.id], view:'password')
    }

    def loadProtected() {
        def logEntry = logEntryService.get(params.id.toLong())
        try {
            if(params.password) {
                String newContent = ''
                newContent = unprotectContent(logEntry.content, params.password)
                logEntry.content = newContent
                respond logEntry, view:'show', model: [password: params.password.encodeAsBase64().toString()]
            }else{
                respond logEntry.id, view:'password'
            }
        }catch(ex){
            println(ex)
            respond logEntry.id, view:'password'
        }
    }

    def create() {
        def logEntry = new LogEntry(params)
        def result = LogEntry.createCriteria().list() {
            order('date', 'desc')
        }
        result[0].date = result[0].date.plus(1)
        if(!logEntry.date) logEntry.date = result[0].date
        respond logEntry
    }

    def save(LogEntry logEntry) {
        if (logEntry == null) {
            notFound()
            return
        }

        try {
            String newContent = ''
            if(params.password) {
                newContent = protectContent(logEntry.content, params.password)
                logEntry.content = newContent
            }
            logEntryService.save(logEntry)
        } catch (ValidationException e) {
            respond logEntry.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'logEntry.label', default: 'LogEntry'), logEntry.id])
                redirect logEntry
            }
            '*' { respond logEntry, [status: CREATED] }
        }
    }

    def edit(Long id) {
        def logEntry = logEntryService.get(id)
        try {
            if(params.verify && logEntry.isProtected) {
                String newContent = ''
                newContent = unprotectContent(logEntry.content, new String(params.verify.decodeBase64()))
                logEntry.content = newContent
                respond logEntry, view:'edit'
            }else{
                respond logEntry, view:'edit'
            }
        }catch(ex){
            println(ex)
            respond view: 'index'
        }
    }

    def update(LogEntry logEntry) {
        if (logEntry == null) {
            notFound()
            return
        }

        try {
            String newContent = ''
            if(params.password) {
                newContent = protectContent(logEntry.content, params.password)
                logEntry.content = newContent
            }
            logEntryService.save(logEntry)
        } catch (ValidationException e) {
            respond logEntry.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'logEntry.label', default: 'LogEntry'), logEntry.id])
                redirect logEntry
            }
            '*'{ respond logEntry, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        logEntryService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'logEntry.label', default: 'LogEntry'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'logEntry.label', default: 'LogEntry'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    protected String protectContent(String content, String password){
        password = password.padRight(16, password) //new BCryptPasswordEncoder().encode(password)[0..15]
        IvParameterSpec iv = new IvParameterSpec(password.getBytes("UTF-8"))
        SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes("UTF-8"), "AES")
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
        String str=content
        byte[] encrypted = cipher.doFinal(str.getBytes())
        String newContent=encrypted.encodeBase64().toString()
        return newContent
    }

    protected String unprotectContent(String content, String password){
        password = password.padRight(16, password) //new BCryptPasswordEncoder().encode(password)[0..15]
        IvParameterSpec iv = new IvParameterSpec(password.getBytes("UTF-8"))
        SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes("UTF-8"), "AES")
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
        String newContent=new String(cipher.doFinal(content.decodeBase64()))
        return newContent
    }

}
