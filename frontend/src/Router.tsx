import { Route, Routes } from 'react-router-dom';

import KkogkkogList from '@/@pages/kkogkkog-list';
import KkogkkogCreate from '@/@pages/kkogkkog-list/create';
import Landing from '@/@pages/landing';

export const PATH = {
  LANDING: '/',
  KKOGKKOG_LIST: '/kkogkkog-list',
  KKOGKKOG_CREATE: '/kkogkkog-list/create',
  KKOGKKOG_DETAIL: '/kkogkkog-list/:id',
};

const Router = () => {
  return (
    <Routes>
      <Route path={PATH.LANDING} element={<Landing />} />
      <Route path={PATH.KKOGKKOG_LIST} element={<KkogkkogList />} />
      <Route path={PATH.KKOGKKOG_CREATE} element={<KkogkkogCreate />} />
    </Routes>
  );
};

export default Router;
