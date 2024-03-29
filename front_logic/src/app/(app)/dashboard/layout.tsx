
import { Navbar } from "@/modules/navigation/Navbar";

export default function Layout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <div lang="en">
      <Navbar children={children} />

    </div>
  );
}



