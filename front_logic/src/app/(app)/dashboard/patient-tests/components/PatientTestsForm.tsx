import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useValidatedForm } from "@/hooks/useValidatedForm";

import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";
import { useFormStatus } from "react-dom";
import { useForm } from "react-hook-form";
import { useBackPath } from "../../../../../modules/shared/BackButton";
import { DialogClose } from "@/components/ui/dialog";
import {
  formData,
  PatientTestsData,
  PatientTestsform,
} from "@/schema/patient-tests";
import { Textarea } from "@/components/ui/textarea";
import { TestCategoryData } from "@/schema/testcategory";
import { PatientData } from "@/schema/patients";
import {
  getAllPatientsData,
  getChildTestCategories,
} from "@/server_actions/(get-requests)/client/clientside";
import { RefreshCcwDot } from "lucide-react";

const PatientTestsForm = ({ authtoken }: { authtoken?: string }) => {
  const { errors, hasErrors, handleChange, setErrors } =
    useValidatedForm<PatientTestsData>(formData);

  const [getData, setGetData] = useState<boolean>(false);
  const [gotChildrenCategory, setGotChildrenCategory] = useState<
    TestCategoryData[]
  >([]);
  const [gotPatients, setGotPatients] = useState<PatientData[]>([]);

  const form = useForm<PatientTestsform>({
    resolver: zodResolver(formData),
    defaultValues: {
      testTimings: "",
      priority: "",
      clinicalNote: "",
      spclInstruction: "",
      patientInfoId: "",
      testCategoriesId: "",
    },
  });

  const editing = !form.formState.isValid;

  const handleSubmit = async (data: PatientTestsform) => {
    try {
      const payload = {
        testTimings: data.testTimings,
        priority: data.priority,
        clinicalNote: data.clinicalNote,
        spclInstruction: data.spclInstruction,
        patientInfoId: Number(data.patientInfoId),
        testCategoriesId: Number(data.testCategoriesId),
      };
      console.log(payload);

      //   await createPatientTestsAction(payload);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    if (getData === true) {
      const fetchPatients = async () => {
        const patients = await getAllPatientsData(authtoken);
        setGotPatients(patients);
      };
      const fetchCategories = async () => {
        const categories = await getChildTestCategories(authtoken);
        setGotChildrenCategory(categories);
      };
      fetchPatients();
      fetchCategories();
    }
  });

  return (
    <div className="h-[700px] overflow-y-scroll p-5 ">
      <Form {...form}>
        <form onSubmit={form.handleSubmit(handleSubmit)} className="space-y-4">
          <FormField
            control={form.control}
            name="startTime"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Test Timings</FormLabel>
                <FormControl>
                  <Input
                    placeholder="Enter Test Timings"
                    type="datetime-local"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="priority"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Priority</FormLabel>
                <FormControl>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Select Priority" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="High">High</SelectItem>
                      <SelectItem value="Medium">Medium</SelectItem>
                      <SelectItem value="Low">Low</SelectItem>
                    </SelectContent>
                  </Select>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="priority"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Status</FormLabel>
                <FormControl>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Select Status" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="appointed">Appointed</SelectItem>
                      <SelectItem value="rejected">Rejected</SelectItem>
                      <SelectItem value="done">Done</SelectItem>
                      <SelectItem value="progressing">In Progress</SelectItem>
                    </SelectContent>
                  </Select>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="startTime"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Select the Start Date</FormLabel>
                <FormControl>
                  <Input
                    placeholder="Enter Test Timings"
                    type="datetime-local"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="startTime"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Test Timings</FormLabel>
                <FormControl>
                  <Input
                    placeholder="Enter Test Timings"
                    type="datetime-local"
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="clinicalNote"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Clinical Note</FormLabel>
                <FormControl>
                  <Textarea placeholder="Enter Clinical Note" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="spclInstruction"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Special Instruction</FormLabel>
                <FormControl>
                  <Input placeholder="Enter Special Instruction" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="flex items-end ">
              <Button
                onClick={() => setGetData(true)}
                className="rounded-full p-4"
              >
                <RefreshCcwDot className="w-4 h-4" />
              </Button>
            </div>
          <FormField
            control={form.control}
            name="patientInfoId"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Patient Info</FormLabel>
                <FormControl>
                <Select onValueChange={field.onChange} value={field.value}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select Test Category"></SelectValue>
                    </SelectTrigger>
                  </Select>
                </FormControl>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="testCategoriesId"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Please select the test category</FormLabel>
                <FormControl>
                  <Select onValueChange={field.onChange} value={field.value}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select Test Category"></SelectValue>
                    </SelectTrigger>
                  </Select>
                </FormControl>
              </FormItem>
            )}
          />
          <SaveButton editing={editing} errors={hasErrors} />
        </form>
      </Form>
    </div>
  );
};

export default PatientTestsForm;

const SaveButton = ({
  editing,
  errors,
}: {
  editing?: boolean;
  errors?: boolean;
}) => {
  const { pending } = useFormStatus();
  const isCreating = pending && editing === false;
  const isUpdating = pending && editing === true;

  return (
    <div className="mt-4">
      {editing ? (
        <div>
          <Button
            type="submit"
            className="w-64"
            disabled={isCreating || isUpdating || errors}
            aria-disabled={isCreating || isUpdating || errors}
          >
            {editing
              ? `Sav${isUpdating ? "ing..." : "e"}`
              : `Creat${isCreating ? "ing..." : "e"} `}
          </Button>
        </div>
      ) : (
        <div>
          <DialogClose asChild>
            <div>
              <Button
                type="submit"
                className="w-64"
                disabled={isCreating || isUpdating || errors}
                aria-disabled={isCreating || isUpdating || errors}
              >
                {editing
                  ? `Sav${isUpdating ? "ing..." : "e"}`
                  : `Creat${isCreating ? "ing..." : "e"} `}
              </Button>{" "}
            </div>
          </DialogClose>
        </div>
      )}
    </div>
  );
};
