package com.dc.esb.servicegov.security.impl;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Spring/Hibernate sample application's one and only configured Apache Shiro Realm.
 * <p/>
 * <p>Because a Realm is really just a security-specific DAO, we could have just made Hibernate calls directly
 * in the implementation and named it a 'HibernateRealm' or something similar.</p>
 * currentUserInterceptor
 * <p>But we've decided to make the calls to the database using a UserDAO, since a DAO would be used in other areas
 * of a 'real' application in addition to here. We felt it better to use that same DAO to show code re-use.</p>
 */
@Component
@Transactional
public class SampleRealm extends AuthorizingRealm {


    public SampleRealm() {
        setName("SampleRealm"); //This name must match the name in the User class's getPrincipals() method
        setCredentialsMatcher(new Sha256CredentialsMatcher());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

/*
@Transactional
protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
User user = userManager.getUserByName(token.getUsername());
if( user != null ) {
return new SimpleAuthenticationInfo(token.getUsername(), user.getPassword(), getName());
} else {
return null;
}
}


@Transactional
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
String userName = (String) principals.fromRealm(getName()).iterator().next();
User user = userManager.getUserByName(userName);
user.getEmail();
if( user != null ) {
SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
for( Role role : user.getRoles() ) {
info.addRole(role.getName());
info.addStringPermissions( role.getPermissions() );
}
return info;
} else {
return null;
}
}
*/

}


