package dailyLog

class BootStrap {

    def init = { servletContext ->
        //Admin user
        Role.withNewTransaction { section ->
            if(!Role.findByAuthority('ROLE_ADMIN')){
                def adminRole = new Role(authority: 'ROLE_ADMIN').save(failOnError: true, flush: true)
                def testUser = new User(username: 'admin', password: 'admin').save(failOnError: true, flush: true)
                def userRole = new UserRole(user: testUser, role: adminRole).save(failOnError: true, flush: true)
            }
        }
        assert User.count() == 1
        assert Role.count() == 1
        assert UserRole.count() == 1
    }
    def destroy = {
    }
}
