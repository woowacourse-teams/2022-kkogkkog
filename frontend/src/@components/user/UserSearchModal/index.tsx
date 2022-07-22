import { useState } from 'react';

import Modal from '@/@components/@shared/Modal';
import { ANIMATION_DURATION } from '@/constants/animation';
import { UserResponse } from '@/types/remote/response';

import UserSearchForm from '../UserSearchForm';

interface UserSearchModalProps {
  currentReceiverList: UserResponse[];
  onSelectReceiver: (user: UserResponse) => void;
  onCloseModal: () => void;
}

const UserSearchModal = (props: UserSearchModalProps) => {
  const { currentReceiverList, onSelectReceiver, onCloseModal } = props;
  const [animation, setAnimation] = useState(false);

  return (
    <Modal
      position='bottom'
      animation={animation}
      onCloseModal={() => {
        setAnimation(true);
        setTimeout(() => {
          onCloseModal();
        }, ANIMATION_DURATION.modal);
      }}
    >
      <UserSearchForm
        currentReceiverList={currentReceiverList}
        onSelectReceiver={onSelectReceiver}
      />
    </Modal>
  );
};

export default UserSearchModal;
