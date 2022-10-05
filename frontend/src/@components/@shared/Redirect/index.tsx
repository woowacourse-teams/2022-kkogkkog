import { Navigate } from 'react-router-dom';

import { PATH } from '@/Router';

import Loading from '../Loading';

interface RedirectProps {
  code: string | null;
}

const Redirect = (props: RedirectProps) => {
  const { code } = props;

  return code ? <Loading /> : <Navigate to={PATH.MAIN} />;
};

export default Redirect;
