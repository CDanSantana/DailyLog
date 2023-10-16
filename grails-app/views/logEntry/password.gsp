<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'logEntry.label', default: 'LogEntry')}" />
        <title>Password Protected Log Entry</title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#show-logEntry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="show-logEntry" class="col-12 content scaffold-show" role="main">
                    <h1>Registro Protejido</h1>
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:form method="POST" action="loadProtected">
                        <fieldset class="form">
                            <g:hiddenField name="id" value="${id}" />
                            <label for="password">Senha</label>
                            <g:passwordField name="password" value="${password}" required=""/>
                        </fieldset>
                        <fieldset class="buttons">
                            <input class="edit" type="submit" value="Continue" />
                        </fieldset>
                    </g:form>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>
