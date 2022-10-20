import { MouseEventHandler, useState } from 'react';

import Icon from '@/@components/@shared/Icon';
import Modal from '@/@components/@shared/Modal';
import { ANIMATION_DURATION } from '@/constants/animation';
import theme from '@/styles/theme';
import { UserResponse } from '@/types/user/remote';

import UserSearchForm from '../UserSearchForm';
import * as Styled from './style';

interface UserSearchModalProps {
  currentReceiverList: UserResponse[];
  onSelectReceiver: (user: UserResponse) => MouseEventHandler<HTMLButtonElement>;
  closeModal: () => void;
}

const UserSearchModal = (props: UserSearchModalProps) => {
  const { currentReceiverList, onSelectReceiver, closeModal } = props;
  const [animation, setAnimation] = useState(false);

  const onCloseModal = () => {
    setAnimation(true);
    setTimeout(() => {
      closeModal();
    }, ANIMATION_DURATION.modal);
  };

  return (
    <Modal
      position='bottom'
      animation={animation}
      closeModal={onCloseModal}
      css={Styled.UserSearchModal}
    >
      <Styled.CloseButton type='button' onClick={onCloseModal}>
        <Icon iconName='close' size='18' color={theme.colors.dark_grey_200} />
      </Styled.CloseButton>
      <UserSearchForm
        currentReceiverList={currentReceiverList}
        onSelectReceiver={onSelectReceiver}
      />
    </Modal>
  );
};

export default UserSearchModal;
