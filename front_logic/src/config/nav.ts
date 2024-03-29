import { SidebarLink } from "@/components/SidebarItems";
import { Cog, Globe, HomeIcon } from "lucide-react";
import { Building, Accessibility } from "lucide-react";

type AdditionalLinks = {
  title: string;
  links: SidebarLink[];
};

export const defaultLinks: SidebarLink[] = [
  { href: "/dashboard/patients", title: "Patients", icon: Accessibility },
  { href: "/dashboard/employees", title: "Employees", icon: Building },
  { href: "/dashboard/services", title: "Services", icon: Cog },
  { href: "/dashboard/ranks", title: "Ranks", icon: HomeIcon },
  { href: "/dashboard/units", title: "Units", icon: Cog },
  { href: "/dashboard/rooms", title: "Rooms", icon: Cog },

  // { href: "/dashboard/settings", title: "Settings", icon: Cog },
];

// export const additionalLinks: AdditionalLinks[] = [
//   {
//     title: "Entities",
//     links: [
//       {
//         href: "/emps",
//         title: "Emps",
//         icon: Globe,
//       },
//       {
//         href: "/employees",
//         title: "Employees",
//         icon: Globe,
//       },
//       {
//         href: "/units",
//         title: "Units",
//         icon: Globe,
//       },
//       {
//         href: "/ranks",
//         title: "Ranks",
//         icon: Globe,
//       },
//       {
//         href: "/services",
//         title: "Services",
//         icon: Globe,
//       },
//     ],
//   },

// ];
