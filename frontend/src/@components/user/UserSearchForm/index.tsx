import { ChangeEventHandler, MouseEventHandler } from 'react';

import Input from '@/@components/@shared/Input';
import { useSearchUser } from '@/@hooks/ui/user/useSearchUser';
import { UserResponse } from '@/types/user/remote';

import * as Styled from './style';

interface UserSearchFormProps {
  currentReceiverList: UserResponse[];
  onSelectReceiver: (user: UserResponse) => MouseEventHandler<HTMLButtonElement>;
}

const UserSearchForm = (props: UserSearchFormProps) => {
  const { currentReceiverList, onSelectReceiver } = props;

  const { searchedUserList, searchUser } = useSearchUser();

  const onChangeSearchInput: ChangeEventHandler<HTMLInputElement> = e => {
    const {
      target: { value: searchInput },
    } = e;

    searchUser(searchInput);
  };

  return (
    <Styled.Root>
      {currentReceiverList.length !== 0 && (
        <Styled.SelectedUserListContainer>
          {currentReceiverList.map(user => (
            <Styled.SelectedUserContainer key={user.id} onClick={() => onSelectReceiver(user)}>
              <span>{user.nickname}</span>
            </Styled.SelectedUserContainer>
          ))}
        </Styled.SelectedUserListContainer>
      )}

      <Input.HiddenLabel
        label='누구에게 주고 싶나요?'
        placeholder='🔍 유저 검색'
        onChange={onChangeSearchInput}
        autoFocus={true}
      />

      <UserSearchResult
        searchedUserList={searchedUserList}
        currentReceiverList={currentReceiverList}
        onSelectReceiver={onSelectReceiver}
      />
    </Styled.Root>
  );
};

interface UserSearchResultProps {
  searchedUserList: UserResponse[] | undefined;
  currentReceiverList: UserResponse[];
  onSelectReceiver: (user: UserResponse) => MouseEventHandler<HTMLButtonElement>;
}

const UserSearchResult = (props: UserSearchResultProps) => {
  const { searchedUserList, currentReceiverList, onSelectReceiver } = props;

  if (searchedUserList === undefined) {
    return <Styled.TextContainer>🔍 유저를 찾아보세요 🔍</Styled.TextContainer>;
  }

  if (searchedUserList.length === 0) {
    return <Styled.TextContainer>😱 검색된 유저가 존재하지 않습니다. 😱</Styled.TextContainer>;
  }

  return (
    <Styled.SearchedUserContainer>
      {searchedUserList.map(user => {
        const isSelected = currentReceiverList.some(receiver => receiver.id === user.id);

        const ariaLabel = isSelected ? `${user.nickname} 선택 해제` : `${user.nickname} 선택`;

        return (
          <Styled.SearchedUser
            key={user.id}
            isSelected={isSelected}
            onClick={onSelectReceiver(user)}
            aria-label={ariaLabel}
          >
            <Styled.ProfileImage src={user.imageUrl} width='24' alt='프사' />
            <span>{user.nickname}&nbsp;</span>
            <Styled.Email>({user.email})</Styled.Email>
          </Styled.SearchedUser>
        );
      })}
    </Styled.SearchedUserContainer>
  );
};

export default UserSearchForm;
