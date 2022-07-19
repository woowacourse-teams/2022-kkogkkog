import { useQuery } from 'react-query';

import { getUserList } from '@/apis/user';
import { UserListResponse } from '@/types/remote/response';

const useUserList = () => {
  const { data, isLoading, isError, remove } = useQuery<{ data: UserListResponse }>(
    ['getUserList'],
    getUserList,
    {
      suspense: false,
    }
  );

  return {
    userList: data?.data,
    isLoading,
    isError,
    remove,
  };
};

export default useUserList;
