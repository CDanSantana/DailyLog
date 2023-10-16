<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'logEntry.label', default: 'LogEntry')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#edit-logEntry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="edit-logEntry" class="col-12 content scaffold-edit" role="main">
                    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.logEntry}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.logEntry}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
                    <g:form resource="${this.logEntry}" method="PUT">
                        <g:hiddenField name="version" value="${this.logEntry?.version}" />
                        <fieldset class="form">
                            <f:all bean="logEntry"/>
                            <div id="spanPassword" style="display: none">
                                <label>Senha</label>
                                <g:textField name="password"/>
                            </div>
                        </fieldset>
                        <fieldset class="buttons">
                            <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                        </fieldset>
                    </g:form>
                </div>
            </section>
        </div>
    </div>
    
        <script>
        document.addEventListener("DOMContentLoaded", function(event) {
            %{--$("textarea#content").EasyEditor();--}%
            $("textarea#content").qeditor({});
            $("#isProtected").on('click', function() {
                if ($('#isProtected').is(":checked")) {
                    $('#spanPassword').show();
                }else{
                    $('#spanPassword').hide();
                }
            });
            if ($('#isProtected').is(":checked")) {
                $('#spanPassword').show();
            }
        });
    </script>

    </body>
</html>
