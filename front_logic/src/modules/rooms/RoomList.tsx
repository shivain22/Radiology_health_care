"use client";
import { RoomData } from "@/schema/rooms";
import Link from "next/link";
import { useState } from "react";
import Modal from "../shared/Modal";
import { Roomform } from "./RoomForm";
import { Button } from "@/components/ui/button";
import { PlusIcon } from "lucide-react";
import { usePathname } from "next/navigation";
import { cn } from "@/lib/utils";
import { deleteRoomAction } from "@/server_actions/actions/rooms";

type TOpenModal = (room?: RoomData) => void;

export default function RoomList({ rooms }: { rooms: RoomData[]; }) {
  const [open, setOpen] = useState(false);
  const [activeRoom, setActiveRoom] = useState<RoomData | null>(null);

  const openModal = (room?: RoomData) => {
    setOpen(true);
    room && setActiveRoom(room);
  };

  const closeModal = () => {
    setOpen(false);
  };

  return (
    <div>
      <Modal
        open={open}
        setOpen={setOpen}
        title={activeRoom ? "Editing Room" : "Add Room"}
      >
        <div>
          <Roomform
            room={activeRoom}
            openModal={openModal}
            closeModal={closeModal}
          />
        </div>
      </Modal>
      <div className="absolute top-0 right-0">
        <Button onClick={() => openModal()} variant={"outline"}>
          +
        </Button>
      </div>
      {rooms.length === 0 ? (
        <EmptyState openModal={openModal} />
      ) : (
        <ul>
          {rooms.map((room) => (
            <Room room={room} key={room.id} openModal={openModal} />
          ))}
        </ul>
      )}
    </div>
  );
}

const Room = ({
  room,
  openModal,
}: {
  room: RoomData;
  openModal: TOpenModal;
}) => {
  const pathname = usePathname();
  const basePath = pathname.includes("rooms") ? pathname : pathname + "/rooms/";

  return (
    <li className={cn("flex justify-between my-2")}>
      <div className="w-full">
        
        <div>{room.roomNo}</div>
      </div>
      <div className="flex gap-2 mr-5">
        <Button variant={"link"} asChild>
          <Link href={basePath + "/" + room.id}>Edit</Link>
        </Button>
        <Button
          onClick={() => deleteRoomAction(room.id)}
          variant={"destructive"}
        >
          Delete
        </Button>
      </div>
    </li>
  );
};

const EmptyState = ({ openModal }: { openModal: TOpenModal }) => {
  return (
    <div className="test-center">
      <h3 className="mt-2 text-sm font-semibold text-secondary-foreground">
        No rooms
      </h3>
      <p className="mt-1 text-sm text-muted-foreground">
        Get Started by creating a new room.
      </p>
      <div className="mt-6">
        <Button onClick={() => openModal()}>
          <PlusIcon className="h-4" />
          New Room{""}
        </Button>
      </div>
    </div>
  );
};
