<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'logEntry.label', default: 'LogEntry')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#create-logEntry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="create-logEntry" class="col-12 content scaffold-create" role="main">
                    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
                    <g:form resource="${this.logEntry}" method="POST">
                        <fieldset class="form">
                            <f:all bean="logEntry"/>
                            %{--<textarea id="entryContent"></textarea>--}%
                            <div id="spanPassword" style="display: none">
                                <label>Senha</label>
                                <g:textField name="password"/>
                            </div>
                        </fieldset>
                        <fieldset class="buttons">
                            <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
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
        });
    </script>

    </body>
</html>
