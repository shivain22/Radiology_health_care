"use client";
import { useState } from "react";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { cn } from "@/lib/utils";
import Modal from "../shared/Modal";
// import { useOptimisticRooms } from "@/app/(app)/Rooms/useOptimisticRooms";
import { Button } from "@/components/ui/button";
// import RoomForm from "./RoomForm";
import { PlusIcon } from "lucide-react";
import { RoomData } from "@/schema/rooms";

import { deleteRoomAction } from "@/server_actions/actions/rooms";
import { Roomform } from "./RoomForm";

type TOpenModal = (room?: RoomData) => void;
export default function RoomList({ rooms }: { rooms: RoomData[] }) {
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
        title={activeRoom ? "Edit Room" : "Add Room"}
      >
        <div>
          <Roomform room={activeRoom} closeModal={closeModal} openModal={openModal} />
          
        </div>
      </Modal>
      <div className="absolute right-0 top-0 ">
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

//Displaying Individual Room Component
const Room = ({
  room,
  openModal,
}: {
  room: RoomData;
  openModal: TOpenModal;
}) => {
  const pathname = usePathname();
  const basePath = pathname.includes("rooms")
    ? pathname
    : pathname + "/rooms/";

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
    <div className="text-center">
      <h3 className="mt-2 text-sm font-semibold text-secondary-foreground">
        No Rooms
      </h3>
      <p className="mt-1 text-sm text-muted-foreground">
        Get started by creating a new room.
      </p>
      <div className="mt-6">
        <Button onClick={() => openModal()}>
          <PlusIcon className="h-4" /> New Room{" "}
        </Button>
      </div>
    </div>
  );
};
