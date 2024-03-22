import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(request: NextRequest) {
  // console.log(request.url)
  const path = request.nextUrl.pathname;

  const isPublicPath = path === "/signin";
  const token = request.cookies.get("authToken")?.value || "";

  if (isPublicPath && token) {
    return NextResponse.redirect(new URL("/dashboard", request.nextUrl));
  }

  if (!isPublicPath && !token) {
    return NextResponse.redirect(new URL("/signIn", request.nextUrl));
  }
}

export const config = {
  matcher: ["/", "/signin", "/dashboard/:path*"],
};
