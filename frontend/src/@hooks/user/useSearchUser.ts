import { useEffect, useState } from 'react';

import useMe from '@/@hooks/user/useMe';
import useUserList from '@/@hooks/user/useUserList';
import { UserResponse } from '@/types/remote/response';

export const useSearchUser = () => {
  const { data: me } = useMe();

  // 이 부분은 검색 API 도입시 사라지게됨
  const { data } = useUserList();
  const userList = data?.data;

  const [searchedUserList, setSearchedUserList] = useState<UserResponse[] | undefined>();

  const searchUser = (keyword: string) => {
    const users = userList || [];

    if (keyword === '') {
      setSearchedUserList(userList?.filter(({ id }) => me?.id !== id));

      return;
    }

    // 이 부분은 검색 API 도입시 비동기 요청하는 코드로 변경됨
    const findUserList = users.filter(
      ({ id, nickname }) => nickname.includes(keyword) && me?.id !== id
    );

    setSearchedUserList(findUserList);
  };

  useEffect(() => {
    if (!searchedUserList) {
      setSearchedUserList(userList?.filter(({ id }) => me?.id !== id));
    }
  }, [searchedUserList, userList, me]);

  return {
    searchedUserList,
    searchUser,
  };
};
