import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(request: NextRequest) {
  // console.log(request.url)
  const path = request.nextUrl.pathname;

  const isPublicPath = path === "/auth";
  const tokenPresent = request.cookies.has("authToken");


  if (isPublicPath && tokenPresent) {
    //add the ranks path to show up after isPublic path and token is present

    return NextResponse.redirect(new URL("/dashboard", request.nextUrl));
  }

  if (!isPublicPath && !tokenPresent) {
    return NextResponse.redirect(new URL("/auth", request.nextUrl));
  }
}

export const config = {
  matcher: [
    "/",
    "/signin",
    "/dashboard/:path*",
    "/ranks/:path*",
    "/services/:path*",
    "/units/:path*",
    "/employees/:path*",
    "/equipment-mappings/:path*",
    "/equipments/:path*",
    "/patient-tests/:path*",
    "/patients/:path*",
    "/rooms/:path*",
    "/test-categories/:path*",
  ],
};
