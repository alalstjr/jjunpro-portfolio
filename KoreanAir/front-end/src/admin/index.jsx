import React from 'react';

import { Admin, Resource } from 'react-admin';
import { UserList } from './user';
import { PostList, PostEdit, PostCreate } from './post';
import Dashboard from './dashboard';
import authProvider from './authProvider';
import dataProvider from './provider/AccountProvider';

export const AdminComponent = () => (
    <Admin dashboard={Dashboard} authProvider={authProvider} dataProvider={dataProvider}>
        <Resource name="posts" list={PostList} edit={PostEdit} create={PostCreate} />
        <Resource name="account" list={UserList} />
    </Admin>
);