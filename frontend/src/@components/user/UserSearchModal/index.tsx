import { useState } from 'react';

import Modal from '@/@components/@shared/Modal';
import { ANIMATION_DURATION } from '@/constants/animation';
import { UserResponse } from '@/types/remote/response';

import UserSearchForm from '../UserSearchForm';

interface UserSearchModalProps {
  currentReceiverList: UserResponse[];
  onSelectReceiver: (user: UserResponse) => void;
  closeModal: () => void;
}

const UserSearchModal = (props: UserSearchModalProps) => {
  const { currentReceiverList, onSelectReceiver, closeModal } = props;
  const [animation, setAnimation] = useState(false);

  return (
    <Modal
      position='bottom'
      animation={animation}
      closeModal={() => {
        setAnimation(true);
        setTimeout(() => {
          closeModal();
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
